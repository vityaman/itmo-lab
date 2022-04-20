package ru.vityaman.tidb.command;

import java.util.Comparator;
import java.util.List;

import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.data.model.Person;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Printer;
import ru.vityaman.tidb.ui.request.RequestPerson;

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

    private final Input in;
    private final Printer out;
    private final Tickets tickets;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param tickets source collection
     */
    public FilterGreaterThanPersonInteractive(Input in, Printer out,
                                                Tickets tickets) {
        this.in = in;
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        Person person = new RequestPerson().from(in, out);

        tickets.all().stream()
                .filter((ticket) -> comparator.compare(ticket.person(), person) > 0)
                .forEach((ticket) -> {
            out.println(ticket.repr());
        });
    }
}
