package ru.vityaman.tidb.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.vityaman.tidb.data.json.JsonTicket;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.resource.TicketResource;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'insert' command with argument.
 */
public final class InsertArgument implements Executable {
    private final Out out;
    private final Tickets tickets;

    /**
     * @param out where to print out new ticket id and creation date
     * @param tickets collection to edit
     */
    public InsertArgument(Out out, Tickets tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute(Ticket ticket) {
        TicketResource entry = tickets.insertionOf(ticket)
                                      .execute()
                                      .result();
        out.println("New ticket added id: " + entry.id()
                + ", creationDate: " + entry.creationDate());
    }

    @Override
    public void execute(List<Object> args) {
        execute(
            new JsonTicket(
                new HashMap<>(
                    (Map<String, Object>) args.get(0)
                )
            )
        );
    }
}
