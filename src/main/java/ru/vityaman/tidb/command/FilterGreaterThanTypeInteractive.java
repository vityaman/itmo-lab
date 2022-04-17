package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.model.TicketType;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Printer;
import ru.vityaman.tidb.ui.request.RequestPrimitive;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Represents a 'filter_greater_than_type'.
 */
public final class FilterGreaterThanTypeInteractive implements Executable {
    private final Input in;
    private final Printer out;
    private final Supplier<Tickets> tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets collection to edit
     */
    public FilterGreaterThanTypeInteractive(Input in, Printer out,
                                            Supplier<Tickets> tickets) {
        this.in = in;
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        TicketType type = new RequestPrimitive<>("type: ",
                TicketType::valueOf,
                new HashSet<Class<? extends Exception>>() {{
                    add(IllegalArgumentException.class);
                }}).from(in, out);
        tickets.get().all().stream()
                .filter((ticket) -> ticket.type().compareTo(type) > 0)
                .forEach((ticket) -> {
                    out.println(ticket.repr());
                });
    }
}
