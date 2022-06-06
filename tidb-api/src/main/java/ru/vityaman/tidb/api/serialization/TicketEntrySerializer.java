package ru.vityaman.tidb.api.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import ru.vityaman.tidb.api.data.TicketEntry;

public final class TicketEntrySerializer
implements ObjectSerializer<TicketEntry> {
    private TicketSerializer ticketSerializer;

    public TicketEntrySerializer(TicketSerializer ticketSerializer) {
        this.ticketSerializer = ticketSerializer;
    }

    @Override
    public TicketEntry read(ObjectInputStream stream) 
    throws IOException, DeserializationException {
        return ObjectSerializer.deserialized(() -> {
            return new TicketEntry(
                stream.readInt(), 
                (Date) stream.readObject(), 
                ticketSerializer.read(stream)
            );
        });
    }

    @Override
    public void write(ObjectOutputStream stream, TicketEntry object) 
    throws IOException {
        stream.writeInt(object.id());
        stream.writeObject(object.creationDate());
        ticketSerializer.write(stream, object);
    }

    public TicketSerializer ticketSerializator() {
        return ticketSerializer;
    }    
}
