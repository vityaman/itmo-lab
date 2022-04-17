package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.ui.printer.Printer;

import java.util.List;
import java.util.function.Supplier;

/**
 * Represents a 'all' command, that prints
 * all tickets from collection.
 */
public final class All implements Executable {
    private final Printer printer;
    private final Supplier<Tickets> tickets;


    /**
     * @param printer where to print.
     * @param tickets source of collection.
     */
    public All(Printer printer, Supplier<Tickets> tickets) {
        this.printer = printer;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        for (TicketEntry ticket : tickets.get().all()) {
            printer.println(ticket.repr());
        }
    }
}
