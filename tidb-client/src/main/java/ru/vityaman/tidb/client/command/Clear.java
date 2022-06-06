package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;

import java.io.IOException;
import java.util.List;

/**
 * Represents a command 'clear' to
 * delete all tickets from the collection.
 */
public final class Clear implements Executable {
    private final RemoteTicketCollection tickets;

    /**
     * @param tickets source of collection
     */
    public Clear(RemoteTicketCollection tickets) {
        this.tickets = tickets;
    }

    public void execute() throws IOException {
        tickets.clear();
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
