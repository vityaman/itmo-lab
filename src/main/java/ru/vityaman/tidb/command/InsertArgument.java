package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.exception.CollectionException;
import ru.vityaman.tidb.collection.json.serialize.TicketSerialization;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'insert' command with argument.
 */
public final class InsertArgument implements Executable {
    private final Out out;
    private final TicketCollection tickets;
    private final TicketSerialization serialization;

    /**
     * @param out where to print out new ticket id and creation date
     * @param tickets collection to edit
     */
    public InsertArgument(
        Out out, 
        TicketCollection tickets, 
        TicketSerialization serialization
    ) {
        this.out = out;
        this.tickets = tickets;
        this.serialization = serialization;
    }

    private void execute(String key, Ticket ticket) throws CollectionException {
        TicketEntry inserted = tickets.insert(key, ticket);
        out.println(String.format(
            "New ticket added id: %s, creationDate: %s",
            inserted.id(), inserted.creationDate()
        ));
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute(
                (String) args.get(0),
                serialization.deserialized(
                    (JsonObject) args.get(1)
                )
            );
        } catch (JsonDeserializationException 
                | CollectionException e) {
            throw new ExecutionException(e);
        }
    }
}
