package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Represents a command 'clear' to
 * delete all tickets from the collection.
 */
public final class Clear implements Executable {
    private final Supplier<Tickets> tickets;

    /**
     * @param tickets source of collection
     */
    public Clear(Supplier<Tickets> tickets) {
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        Tickets collection = tickets.get();
        collection.all()
                .stream()
                .map(TicketEntry::id)
                .collect(Collectors.toList())
                .forEach(collection::removeWithById);
    }
}
