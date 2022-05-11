package ru.vityaman.tidb.command;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.input.RequestObject;
import ru.vityaman.tidb.ui.out.Out;

public final class RemoveLowerThanInteractive implements Executable {
    private static final Comparator<TicketEntry> ticketComparison = 
        Comparator.comparing(Ticket::name);

    private final RequestObject request;
    private final TicketCollection tickets;

    public RemoveLowerThanInteractive(Input in, Out out, TicketCollection tickets) {
        this.request = new RequestObject(in, out);
        this.tickets = tickets;
    }

    public void execute() {
        Ticket given = request.ticket();
        tickets.removeAllThoseLessThan(
            new TicketEntry(0, new Date(), given), 
            ticketComparison
        );
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        execute();
    }
    
}
