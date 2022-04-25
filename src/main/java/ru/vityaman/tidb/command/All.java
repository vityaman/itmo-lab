package ru.vityaman.tidb.command;

import java.util.List;
import java.util.function.Supplier;

import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'all' command, that prints
 * all tickets from collection.
 */
public final class All implements Executable {
    private final Out out;
    private final Supplier<Tickets> tickets;

    /**
     * @param out where to print.
     * @param tickets source of collection.
     */
    public All(Out out, Supplier<Tickets> tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() {
        tickets.get().all().forEach((ticket) -> {
            out.println(ticket.representation());
        });
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
