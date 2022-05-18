package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.exception.CollectionException;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.input.RequestObject;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'insert' command with user input.
 */
public final class InsertInteractive implements Executable {
    private final RequestObject request;
    private final Out out;
    private final TicketCollection tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets collection to edit
     */
    public InsertInteractive(
        Input in, Out out, TicketCollection tickets) {
        this.request = new RequestObject(in, out);
        this.out = out;
        this.tickets = tickets;
    }

    private void execute(String key) 
    throws CollectionException {
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
        } catch (CollectionException e) {
            throw new ExecutionException(e);
        }
    }
}
