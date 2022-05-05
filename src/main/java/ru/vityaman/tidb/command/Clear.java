package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.lang.interpreter.Executable;

/**
 * Represents a command 'clear' to
 * delete all tickets from the collection.
 */
public final class Clear implements Executable {
    private final TicketCollection tickets;

    /**
     * @param tickets source of collection
     */
    public Clear(TicketCollection tickets) {
        this.tickets = tickets;
    }

    public void execute() {
        tickets.clear();
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
