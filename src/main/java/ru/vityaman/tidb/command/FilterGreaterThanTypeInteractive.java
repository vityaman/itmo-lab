package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.data.TicketType;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.input.RequestPrimitive;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'filter_greater_than_type'.
 */
public final class FilterGreaterThanTypeInteractive implements Executable {
    private final RequestPrimitive primitive;
    private final Out out;
    private final TicketCollection tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets collection to edit
     */
    public FilterGreaterThanTypeInteractive(
        Input in, Out out, TicketCollection tickets
    ) {
        this.primitive = new RequestPrimitive(in, out);
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() {
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
    public void execute(List<Object> args) {
        execute();
    }
}
