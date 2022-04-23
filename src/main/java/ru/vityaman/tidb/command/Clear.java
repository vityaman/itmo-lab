package ru.vityaman.tidb.command;

import java.util.List;
import java.util.stream.Collectors;

import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;

/**
 * Represents a command 'clear' to
 * delete all tickets from the collection.
 */
public final class Clear implements Executable {
    private final Tickets tickets;

    /**
     * @param tickets source of collection
     */
    public Clear(Tickets tickets) {
        this.tickets = tickets;
    }

    public void execute() {
        tickets.all()
            .stream()
            .map(TicketEntry::id)
            .collect(Collectors.toList())
            .forEach(tickets::removeWithById);
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
