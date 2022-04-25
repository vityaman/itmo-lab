package ru.vityaman.tidb.command;

import java.util.List;
import java.util.function.Supplier;

import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Out;
import ru.vityaman.tidb.ui.request.RequestTicket;

/**
 * Represents a 'insert' command with user input.
 */
public final class InsertInteractive implements Executable {
    private final Input in;
    private final Out out;
    private final Supplier<Tickets> tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets collection to edit
     */
    public InsertInteractive(
        Input in, Out out, Supplier<Tickets> tickets) {
        this.in = in;
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() {
        Ticket ticket = new RequestTicket().from(in, out);
        TicketEntry entry = tickets.get()
                .insertionOf(ticket)
                .execute()
                .result();
        out.print("New ticket added with id: " + entry.id()
                + " and creationDate: " + entry.creationDate() + "\n");
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
