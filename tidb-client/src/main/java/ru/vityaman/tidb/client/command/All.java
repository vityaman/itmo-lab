package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.ui.out.Out;

import java.io.IOException;
import java.util.List;


/**
 * Represents a 'all' command, that prints
 * all tickets from collection.
 */
public final class All implements Executable {
    private final Out out;
    private final RemoteTicketCollection tickets;

    /**
     * @param out where to print.
     * @param tickets source of collection.
     */
    public All(Out out, RemoteTicketCollection tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() throws IOException {
        tickets.all().forEach((e) ->
            out.println(
                e.key() + ": " + e.ticket().toString()
            )
        );
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
