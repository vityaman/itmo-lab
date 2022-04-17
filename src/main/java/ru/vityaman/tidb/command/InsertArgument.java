package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.json.JsonTicket;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.ui.printer.Printer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Represents a 'insert' command with argument.
 */
public final class InsertArgument implements Executable {
    private final Printer out;
    private final Supplier<Tickets> tickets;

    /**
     * @param out where to print out new ticket id and creation date
     * @param tickets collection to edit
     */
    public InsertArgument(Printer out, Supplier<Tickets> tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        Ticket ticket = new JsonTicket(
                new HashMap<>((Map<String, Object>) args.get(0)));
        TicketEntry entry = tickets.get()
                .insertionOf(ticket)
                .execute()
                .result();
        out.println("New ticket added id: " + entry.id()
                        + ", creationDate: " + entry.creationDate());
    }
}
