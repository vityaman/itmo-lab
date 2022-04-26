package ru.vityaman.tidb.command;

import java.util.List;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.data.resource.exception.ResourceNotFoundException;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;

/**
 * Represents a 'remove' command.
 */
public final class RemoveById implements Executable {
    private final Tickets tickets;

    /**
     * @param tickets tickets to edit
     */
    public RemoveById(Tickets tickets) {
        this.tickets = tickets;
    }

    private void execute(int id) throws ExecutionException {
        try {
            tickets.removeWithById(id);
        } catch (ResourceNotFoundException e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        execute((Integer) args.get(0));
    }
}
