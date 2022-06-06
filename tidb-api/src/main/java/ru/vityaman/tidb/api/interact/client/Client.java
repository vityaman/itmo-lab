package ru.vityaman.tidb.api.interact.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.vityaman.tidb.api.collection.Entry;
import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.data.Person;
import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketEntry;
import ru.vityaman.tidb.api.data.TicketType;
import ru.vityaman.tidb.api.exception.CapacityExceededException;
import ru.vityaman.tidb.api.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.api.exception.NoSuchEntryException;
import ru.vityaman.tidb.api.interact.MethodCall;
import ru.vityaman.tidb.api.interact.Result;
import ru.vityaman.tidb.api.interact.ServerError;
import ru.vityaman.tidb.api.interact.net.EstablishedUDPConnection;
import ru.vityaman.tidb.api.interact.net.EstablishedConnection;
import ru.vityaman.tidb.api.method.Method;
import ru.vityaman.tidb.api.serialization.DeserializationException;
import ru.vityaman.tidb.api.serialization.EntrySetSerializer;
import ru.vityaman.tidb.api.serialization.GroupsByCreationDateSerializer;
import ru.vityaman.tidb.api.serialization.SerializationStream;
import ru.vityaman.tidb.api.serialization.TicketEntrySerializer;
import ru.vityaman.tidb.api.serialization.TicketSerializer;

public final class Client implements Closeable {
    private final EstablishedConnection connection;

    private final EntrySetSerializer entriesSerializator;
    private final TicketEntrySerializer ticketEntrySerializer;
    private final TicketSerializer ticketSerializer;
    private final GroupsByCreationDateSerializer groupsByCreationDateSerializer;

    public Client(SocketAddress service, EntrySetSerializer entriesSerializator)
    throws SocketException {
        connection = new EstablishedUDPConnection(service);
        this.entriesSerializator = entriesSerializator;
        this.ticketEntrySerializer = entriesSerializator.ticketEntrySerializator();
        this.ticketSerializer = ticketEntrySerializer.ticketSerializator();
        this.groupsByCreationDateSerializer =
            new GroupsByCreationDateSerializer(ticketEntrySerializer);
    }

    public RemoteTicketCollection collection() {
        return new Collection();
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }

    private final class Collection implements RemoteTicketCollection {

        @Override
        public java.util.Collection<Entry> all() throws IOException {
            Result result = callMethod(Method.ALL, 
                SerializationStream.empty()
            );
            try {
                return entriesSerializator.read(result.payload());
            } catch (DeserializationException e) {
                throw new BadResponseException(e.getMessage(), e);
            }
        }

        @Override
        public TicketEntry insert(String key, Ticket ticket)
        throws EntryAlreadyExistsException, CapacityExceededException, IOException {
            Result result = callMethod(Method.INSERT, 
                SerializationStream.empty()
                    .writeSerializable(key)
                    .writeUsingSerializer(ticket, ticketSerializer)
            );
            if (result.error().isPresent()) {
                ServerError error = result.error().get();
                if (error.type() == ServerError.Type.EntryAlreadyExists) {
                    throw new EntryAlreadyExistsException(error.message());
                } 
                if (error.type() == ServerError.Type.CapacityExceeded) {
                    throw new CapacityExceededException(error.message());
                } 
                throw new BadResponseException(error.message());
            }
            try {
                return ticketEntrySerializer.read(result.payload());
            } catch (DeserializationException e) {
                throw new BadResponseException(e.getMessage(), e);
            }
        }

        @Override
        public void updateById(int id, Ticket ticket) 
        throws NoSuchEntryException, IOException {
            Result result = callMethod(Method.UPDATE_BY_ID, 
                SerializationStream.empty()
                    .writeInteger(id)
                    .writeUsingSerializer(ticket, ticketSerializer)
            );
            if (result.error().isPresent()) {
                ServerError error = result.error().get();
                if (error.type() == ServerError.Type.NoSuchEntryException) {
                    throw new NoSuchEntryException(error.message());
                } 
                throw new BadResponseException(error.message());
            }
        }

        @Override
        public void clear() throws IOException {
            Result result = callMethod(Method.CLEAR,
                SerializationStream.empty()
            );
            if (result.error().isPresent()) {
                throw new BadResponseException(result.error().get().message());
            }
        }

        @Override
        public void removeEntryWithKey(String key)
        throws NoSuchEntryException, IOException {
            Result result = callMethod(Method.REMOVE_ENTRY_WITH_KEY, 
                SerializationStream.empty()
                    .writeSerializable(key)
            );
            if (result.error().isPresent()) {
                ServerError error = result.error().get();
                if (error.type() == ServerError.Type.NoSuchEntryException) {
                    throw new NoSuchEntryException(error.message());
                }
                throw new BadResponseException(error.message());
            }
        }

        @Override
        public void removeAllThoseLessThan(TicketEntry given) throws IOException {
            Result result = callMethod(Method.REMOVE_ALL_THOSE_LESS_THAN, 
            SerializationStream.empty()
                .writeUsingSerializer(given, ticketEntrySerializer)

            );
            if (result.error().isPresent()) {
                throw new BadResponseException(result.error().get().message());
            }       
        }

        @Override
        public void removeAllThoseWithKeyLessThan(String given) throws IOException {
            Result result = callMethod(Method.REMOVE_ALL_THOSE_KEY_LESS_THAN, 
                SerializationStream.empty()
                    .writeSerializable(given)
            );
            if (result.error().isPresent()) {
                throw new BadResponseException(result.error().get().message());
            }     
        }

        @Override
        public Map<LocalDate, List<TicketEntry>> entriesGroupedByCreationDate() 
        throws IOException {
            Result result = callMethod(Method.ENTRIES_GROUPED_BY_CREATION_DATE, 
                SerializationStream.empty()
            );
            if (result.error().isPresent()) {
                throw new BadResponseException(result.error().get().message());
            }     
            try {
                return groupsByCreationDateSerializer.read(result.payload());
            } catch (DeserializationException e) {
                throw new BadResponseException(e);
            }
        }

        @Override
        public int countOfEntriesWithPersonGreaterThan(Person given)
        throws IOException {
            Result result = callMethod(Method.COUNT_OF_ENTRIES_WITH_PERSON_GREATER_THAN, 
                SerializationStream.empty()
            );
            connection.receive();
            if (result.error().isPresent()) {
                throw new BadResponseException(result.error().get().message());
            }     
            int count = result.payload().readInt();
            if (count < 0) {
                throw new BadResponseException("Count must be not negative");
            }
            return count;
        }

        @Override
        public java.util.Collection<TicketEntry> 
        entriesWithTypeGreaterThan(TicketType given) throws IOException {
            Result result = callMethod(Method.ENTRIES_WITH_TYPE_GREATER_THAN, 
                SerializationStream.empty()
            );
            try {
                return entriesSerializator.read(result.payload()).stream()
                        .map(Entry::ticket)
                        .collect(Collectors.toSet());
            } catch (DeserializationException e) {
                throw new BadResponseException(e.getMessage(), e);
            }
        }

        private Result callMethod(Method method, SerializationStream stream) 
        throws IOException {
            return Result.deserialized(connection.send(
                    SerializationStream.empty().writeSerializable(
                        new MethodCall(method, stream)
                    )
                ).receive()
            );
        }
    }
}
