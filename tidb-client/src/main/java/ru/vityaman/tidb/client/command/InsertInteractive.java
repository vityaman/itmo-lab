package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketEntry;
import ru.vityaman.tidb.api.exception.CollectionException;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.ui.input.Input;
import ru.vityaman.tidb.client.ui.input.RequestObject;
import ru.vityaman.tidb.client.ui.out.Out;

import java.io.IOException;
import java.util.List;


/**
 * Represents a 'insert' command with user input.
 */
public final class InsertInteractive implements Executable {
    private final RequestObject request;
    private final Out out;
    private final RemoteTicketCollection tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets collection to edit
     */
    public InsertInteractive(
            Input in, Out out, RemoteTicketCollection tickets) {
        this.request = new RequestObject(in, out);
        this.out = out;
        this.tickets = tickets;
    }

    private void execute(String key)
            throws CollectionException, IOException {
        Ticket ticket = request.ticket();
        TicketEntry entry = tickets.insert(key, ticket);
        out.println(String.format(
            "New ticket added with id: %s and creationDate: %s",
            entry.id(), entry.creationDate()
        ));
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute((String) args.get(0));
        } catch (CollectionException | IOException e) {
            throw new ExecutionException(e);
        }
    }
}
