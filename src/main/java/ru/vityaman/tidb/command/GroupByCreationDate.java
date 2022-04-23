package ru.vityaman.tidb.command;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents 'group_by_creation_date' command.
 */
public final class GroupByCreationDate implements Executable {
    private final Out out;
    private final Tickets tickets;

    /**
     * @param out where to print out.
     * @param tickets collection to edit.
     */
    public GroupByCreationDate(Out out, Tickets tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        Map<Date, List<TicketEntry>> ticketsByCreationDate = tickets
                .all().stream()
                .collect(Collectors.groupingBy(TicketEntry::creationDate));
        ticketsByCreationDate.forEach((date, tickets) -> {
            out.println(date.toString(), " ", String.valueOf(tickets.size()));
            tickets.forEach((ticket) -> {
                out.println(ticket.repr());
            });
        });
    }
}
