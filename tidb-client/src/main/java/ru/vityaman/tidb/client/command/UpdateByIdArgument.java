package ru.vityaman.tidb.client.command;


import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.exception.NoSuchEntryException;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.serialization.VionTicketSerializer;
import ru.vityaman.vion.VionObject;
import ru.vityaman.vion.exception.DeserializationException;

import java.io.IOException;
import java.util.List;

/**
 * Represents 'update' command with argument.
 */
public final class UpdateByIdArgument implements Executable {
    private final RemoteTicketCollection tickets;
    private final VionTicketSerializer serialization;

    /**
     * @param tickets collection to edit
     */
    public UpdateByIdArgument(
        RemoteTicketCollection tickets,
        VionTicketSerializer serialization
    ) {
        this.tickets = tickets;
        this.serialization = serialization;
    }

    private void execute(int id, Ticket ticket) throws NoSuchEntryException, IOException {
        tickets.updateById(id, ticket);
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute(
                (Integer) args.get(0),
                serialization.deserialize(
                    (VionObject) args.get(1)
                )
            );
        } catch (DeserializationException
                 | IOException
                 | NoSuchEntryException e) {
            throw new ExecutionException(e);
        }
    }
}
