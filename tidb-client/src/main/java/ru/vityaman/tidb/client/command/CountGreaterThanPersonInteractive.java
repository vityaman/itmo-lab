package ru.vityaman.tidb.client.command;

import java.io.IOException;
import java.util.List;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.data.Person;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.ui.input.Input;
import ru.vityaman.tidb.client.ui.input.RequestObject;
import ru.vityaman.tidb.client.ui.out.Out;

/**
 * Represents a command 'filter_greater_than_person'.
 */
public final class CountGreaterThanPersonInteractive implements Executable {
    private final RequestObject request;
    private final Out out;
    private final RemoteTicketCollection tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets source collection
     */
    public CountGreaterThanPersonInteractive(
            Input in, Out out, RemoteTicketCollection tickets
    ) {
        this.request = new RequestObject(in, out);
        this.out = out;
        this.tickets = tickets;
    }

    public void execute() throws IOException {
        Person given = request.person();
        out.println(
            "Count: " +
            tickets.countOfEntriesWithPersonGreaterThan(given)
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
