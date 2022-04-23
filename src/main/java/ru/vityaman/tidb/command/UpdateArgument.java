package ru.vityaman.tidb.command;

import java.util.List;
import java.util.Map;

import ru.vityaman.tidb.data.json.JsonTicket;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;

/**
 * Represents 'update' command with argument.
 */
public final class UpdateArgument implements Executable {
    private final Tickets tickets;

    /**
     * @param tickets collection to edit
     */
    public UpdateArgument(Tickets tickets) {
        this.tickets = tickets;
    }

    private void execute(int id, Ticket ticket) {
        tickets.ticketWithId(id).updateUsing(ticket);
    }

    @Override
    public void execute(List<Object> args) {
        execute(
            (Integer) args.get(0),
            new JsonTicket((Map<String, Object>) args.get(1))
        );
    }
}
