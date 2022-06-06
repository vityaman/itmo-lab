package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.data.TicketType;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.ui.input.Input;
import ru.vityaman.tidb.client.ui.input.RequestPrimitive;
import ru.vityaman.tidb.client.ui.out.Out;

import java.io.IOException;
import java.util.List;


/**
 * Represents a 'filter_greater_than_type'.
 */
public final class FilterGreaterThanTypeInteractive implements Executable {
    private final RequestPrimitive primitive;
    private final Out out;
    private final RemoteTicketCollection tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets collection to edit
     */
    public FilterGreaterThanTypeInteractive(
            Input in, Out out, RemoteTicketCollection tickets
    ) {
        this.primitive = new RequestPrimitive(in, out);
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() throws IOException {
        TicketType[] typePlace = new TicketType[1];
        primitive.request(
            RequestPrimitive.enumFor(
                "type", 
                TicketType.class
            ), 
            (t) -> typePlace[0] = t
        );        
        TicketType given = typePlace[0];
        tickets.entriesWithTypeGreaterThan(given)
            .forEach(out::println);
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
