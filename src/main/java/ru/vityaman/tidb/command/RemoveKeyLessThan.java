package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.lang.interpreter.Executable;

/**
 * Represents 'remove_id_less_than' command.
 */
public final class RemoveKeyLessThan implements Executable {
    private final TicketCollection tickets;

    /**
     * @param tickets tickets collection to edit
     */
    public RemoveKeyLessThan(TicketCollection tickets) {
        this.tickets = tickets;
    }

    private void execute(String given) {
        tickets.removeAllThoseWithKeyLessThan(
            given, 
            (a, b) -> a.compareTo(b)
        );
    }

    @Override
    public void execute(List<Object> args) {
        execute((String) args.get(0));
    }
}
