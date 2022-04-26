package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.resource.TicketResource;
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
    private final Tickets tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets collection to edit
     */
    public InsertInteractive(
        Input in, Out out, Tickets tickets) {
        this.in = in;
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() {
        Ticket ticket = new RequestTicket().from(in, out);
        TicketResource entry = tickets.insertionOf(ticket)
                                      .execute()
                                      .result();
        out.println(String.format(
            "New ticket added with id: %s and creationDate: %s",
            entry.id(), entry.creationDate())
        );
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
