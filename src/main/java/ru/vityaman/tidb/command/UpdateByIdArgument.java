package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;
import ru.vityaman.tidb.collection.json.serialize.TicketSerialization;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;

/**
 * Represents 'update' command with argument.
 */
public final class UpdateByIdArgument implements Executable {
    private final TicketCollection tickets;
    private final TicketSerialization serialization;

    /**
     * @param tickets collection to edit
     */
    public UpdateByIdArgument(
        TicketCollection tickets, 
        TicketSerialization serialization
    ) {
        this.tickets = tickets;
        this.serialization = serialization;
    }

    private void execute(int id, Ticket ticket) throws NoSuchEntryException {
        tickets.updateById(id, ticket);
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute(
                (Integer) args.get(0),
                serialization.deserialized(
                    (JsonObject) args.get(1)
                )
            );
        } catch (JsonDeserializationException
                | NoSuchEntryException e) {
            throw new ExecutionException(e);
        }
    }
}
