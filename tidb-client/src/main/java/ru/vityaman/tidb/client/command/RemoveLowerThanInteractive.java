package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketEntry;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.ui.input.Input;
import ru.vityaman.tidb.client.ui.input.RequestObject;
import ru.vityaman.tidb.client.ui.out.Out;

import java.io.IOException;
import java.util.Date;
import java.util.List;


public final class RemoveLowerThanInteractive implements Executable {

    private final RequestObject request;
    private final RemoteTicketCollection tickets;

    public RemoveLowerThanInteractive(
            Input in, Out out, RemoteTicketCollection tickets
    ) {
        this.request = new RequestObject(in, out);
        this.tickets = tickets;
    }

    public void execute() throws IOException {
        Ticket given = request.ticket();
        tickets.removeAllThoseLessThan(new TicketEntry(0, new Date(), given));
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute();
        } catch (IOException e) {
            throw new ExecutionException(e);
        }
    }
    
}
