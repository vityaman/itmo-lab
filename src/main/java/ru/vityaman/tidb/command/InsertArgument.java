package ru.vityaman.tidb.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.vityaman.tidb.data.json.JsonTicket;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.printer.Printer;

/**
 * Represents a 'insert' command with argument.
 */
public final class InsertArgument implements Executable {
    private final Printer out;
    private final Tickets tickets;

    /**
     * @param out where to print out new ticket id and creation date
     * @param tickets collection to edit
     */
    public InsertArgument(Printer out, Tickets tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        Ticket ticket = new JsonTicket(
                new HashMap<>((Map<String, Object>) args.get(0)));
        TicketEntry entry = tickets
                .insertionOf(ticket)
                .execute()
                .result();
        out.println("New ticket added id: " + entry.id()
                        + ", creationDate: " + entry.creationDate());
    }
}
