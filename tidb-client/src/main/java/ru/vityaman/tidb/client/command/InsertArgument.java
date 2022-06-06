package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.collection.TicketCollection;
import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketEntry;
import ru.vityaman.tidb.api.exception.CollectionException;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.serialization.VionTicketSerializer;
import ru.vityaman.tidb.client.ui.out.Out;
import ru.vityaman.vion.VionObject;
import ru.vityaman.vion.exception.DeserializationException;

import java.io.IOException;
import java.util.List;

/**
 * Represents a 'insert' command with argument.
 */
public final class InsertArgument implements Executable {
    private final Out out;
    private final RemoteTicketCollection tickets;
    private final VionTicketSerializer serialization;

    /**
     * @param out where to print out new ticket id and creation date
     * @param tickets collection to edit
     */
    public InsertArgument(
        Out out, 
        RemoteTicketCollection tickets,
        VionTicketSerializer serialization
    ) {
        this.out = out;
        this.tickets = tickets;
        this.serialization = serialization;
    }

    private void execute(String key, Ticket ticket) throws CollectionException, IOException {
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
                serialization.deserialize(
                    (VionObject) args.get(1)
                )
            );
        } catch (DeserializationException
                 | IOException
                 | CollectionException e) {
            throw new ExecutionException(e);
        }
    }
}
