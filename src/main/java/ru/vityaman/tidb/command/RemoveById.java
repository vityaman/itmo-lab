package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.resource.Tickets;

import java.util.List;
import java.util.function.Supplier;

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

    @Override
    public void execute(List<Object> args) {
        int id = (Integer) args.get(0);
        tickets.get().removeWithById(id);
    }
}
