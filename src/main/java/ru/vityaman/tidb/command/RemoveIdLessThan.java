package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Represents 'remove_id_less_than' command.
 */
public final class RemoveIdLessThan implements Executable {
    private final Supplier<Tickets> tickets;

    /**
     * @param tickets tickets collection to edit
     */
    public RemoveIdLessThan(Supplier<Tickets> tickets) {
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        int given = (Integer) args.get(0);
        Tickets tickets = this.tickets.get();
        tickets.all().stream()
                .map(TicketEntry::id)
                .filter((id) -> id < given)
                .collect(Collectors.toList())
                .forEach(tickets::removeWithById);
    }
}
