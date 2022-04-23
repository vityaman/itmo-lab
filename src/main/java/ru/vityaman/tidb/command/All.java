package ru.vityaman.tidb.command;

import java.util.List;

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
    private final Tickets tickets;


    /**
     * @param out where to print.
     * @param tickets source of collection.
     */
    public All(Out out, Tickets tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        for (TicketEntry ticket : tickets.all()) {
            out.println(ticket.repr());
        }
    }
}
