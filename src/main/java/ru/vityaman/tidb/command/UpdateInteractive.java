package ru.vityaman.tidb.command;

import java.util.List;
import java.util.function.Supplier;

import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.resource.TicketResource;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Out;
import ru.vityaman.tidb.ui.request.RequestTicket;

/**
 * Represents 'update' command.
 */
public final class UpdateInteractive implements Executable {
    private final Input in;
    private final Out out;
    private final Supplier<Tickets> tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets tickets to edit
     */
    public UpdateInteractive(Input in, Out out, Supplier<Tickets> tickets) {
        this.in = in;
        this.out = out;
        this.tickets = tickets;
    }

    private void execute(int id) {
        TicketResource resource = tickets.get().ticketWithId(id);
        Ticket data = new RequestTicket().from(in, out);
        resource.updateUsing(data);
    }

    @Override
    public void execute(List<Object> args) {
        execute((Integer) args.get(0));
    }
}
