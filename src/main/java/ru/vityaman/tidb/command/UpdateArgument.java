package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.json.JsonTicket;
import ru.vityaman.tidb.data.resource.Tickets;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Represents 'update' command with argument.
 */
public final class UpdateArgument implements Executable {
    private final Supplier<Tickets> tickets;

    /**
     * @param tickets collection to edit
     */
    public UpdateArgument(Supplier<Tickets> tickets) {
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        int id = (Integer) args.get(0);
        JsonTicket ticket = new JsonTicket((Map<String, Object>) args.get(1));
        tickets.get().ticketWithId(id).updateUsing(ticket);
    }
}
