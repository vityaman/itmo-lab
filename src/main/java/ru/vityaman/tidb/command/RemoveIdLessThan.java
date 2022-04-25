package ru.vityaman.tidb.command;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;

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

    private void execute(int id) {
        Tickets tickets = this.tickets.get();
        tickets.all().stream()
                .map(TicketEntry::id)
                .filter((otherId) -> otherId < id)
                .collect(Collectors.toList())
                .forEach(tickets::removeWithById);
    }

    @Override
    public void execute(List<Object> args) {
        execute((Integer) args.get(0));
    }
}
