package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;

import java.io.IOException;
import java.util.List;

/**
 * Represents 'remove_id_less_than' command.
 */
public final class RemoveKeyLessThan implements Executable {
    private final RemoteTicketCollection tickets;

    /**
     * @param tickets tickets collection to edit
     */
    public RemoveKeyLessThan(RemoteTicketCollection tickets) {
        this.tickets = tickets;
    }

    private void execute(String given) throws IOException {
        tickets.removeAllThoseWithKeyLessThan(given);
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute((String) args.get(0));
        } catch (IOException e) {
            throw new ExecutionException(e);
        }
    }
}
