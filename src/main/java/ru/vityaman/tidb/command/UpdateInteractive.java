package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.resource.TicketResource;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Printer;
import ru.vityaman.tidb.ui.request.RequestTicket;

import java.util.List;
import java.util.function.Supplier;

/**
 * Represents 'update' command.
 */
public final class UpdateInteractive implements Executable {
    private final Input in;
    private final Printer out;
    private final Supplier<Tickets> tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets tickets to edit
     */
    public UpdateInteractive(Input in, Printer out,
                             Supplier<Tickets> tickets) {
        this.in = in;
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        int id = (Integer) args.get(0);
        TicketResource resource = tickets.get().ticketWithId(id);
        Ticket data = new RequestTicket().from(in, out);
        resource.updateUsing(data);
    }
}
