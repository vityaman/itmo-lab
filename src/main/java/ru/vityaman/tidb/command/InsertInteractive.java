package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Printer;
import ru.vityaman.tidb.ui.request.RequestTicket;

import java.util.List;
import java.util.function.Supplier;

/**
 * Represents a 'insert' command with user input.
 */
public final class InsertInteractive implements Executable {
    private final Input in;
    private final Printer out;
    private final Supplier<Tickets> tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets collection to edit
     */
    public InsertInteractive(Input in, Printer out,
                             Supplier<Tickets> tickets) {
        this.in = in;
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        Ticket ticket = new RequestTicket().from(in, out);
        TicketEntry entry = tickets.get()
                .insertionOf(ticket)
                .execute()
                .result();
        out.print("New ticket added with id: " + entry.id()
                + " and creationDate: " + entry.creationDate() + "\n");
    }
}
