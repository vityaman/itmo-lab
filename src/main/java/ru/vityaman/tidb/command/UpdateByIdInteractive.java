package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.input.RequestObject;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents 'update' command.
 */
public final class UpdateByIdInteractive implements Executable {
    private final RequestObject request;
    private final TicketCollection tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets2 tickets to edit
     */
    public UpdateByIdInteractive(Input in, Out out, TicketCollection tickets) {
        this.request = new RequestObject(in, out);
        this.tickets = tickets;
    }

    private void execute(int id) throws NoSuchEntryException {
        tickets.updateById(id, request.ticket());
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute((Integer) args.get(0));
        } catch (NoSuchEntryException e) {
            throw new ExecutionException(e);
        }
    }
}
