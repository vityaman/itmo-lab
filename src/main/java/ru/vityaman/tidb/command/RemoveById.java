package ru.vityaman.tidb.command;

import java.util.List;
import java.util.function.Supplier;

import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;

/**
 * Represents a 'remove' command.
 */
public final class RemoveById implements Executable {
    private final Supplier<Tickets> tickets;

    /**
     * @param tickets tickets to edit
     */
    public RemoveById(Supplier<Tickets> tickets) {
        this.tickets = tickets;
    }

    private void execute(int id) {
        tickets.get().removeWithById(id);
    }

    @Override
    public void execute(List<Object> args) {
        execute((Integer) args.get(0));
    }
}
