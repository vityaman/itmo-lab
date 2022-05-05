package ru.vityaman.tidb.command;

import java.util.Comparator;
import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.data.Entry;
import ru.vityaman.tidb.collection.data.Location;
import ru.vityaman.tidb.collection.data.Person;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.input.RequestObject;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a command 'filter_greater_than_person'.
 */
public final class FilterGreaterThanPersonInteractive implements Executable {
    private static final Comparator<Person> comparator =
                Comparator.comparing(Person::passportId)
                    .thenComparing(Person::height)
                    .thenComparing(Person::location,
                            Comparator.comparing(Location::name)
                                    .thenComparing(location ->
                                            location.x() * location.x() +
                                            + location.y() * location.y()
                                            + location.z() * location.z()));

    private final RequestObject request;
    private final Out out;
    private final TicketCollection tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets source collection
     */
    public FilterGreaterThanPersonInteractive(
        Input in, Out out, TicketCollection tickets
    ) {
        this.request = new RequestObject(in, out);
        this.out = out;
        this.tickets = tickets;
    }

    public void execute() {
        Person person = request.person();
        tickets.all().stream()
                .map(Entry::ticket)
                .filter(ticket ->
                    comparator.compare(ticket.person(), person) > 0)
                .forEach(out::println);
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
