package ru.vityaman.tidb.command;

import java.util.List;
import java.util.stream.Collectors;

import ru.vityaman.tidb.data.dto.TicketEntryData;
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

    private void execute() {
        tickets.all().stream()
            .map(TicketEntryData::new)
            .collect(Collectors.groupingBy(TicketEntry::creationDate))
            .forEach((date, tickets) -> {
                out.println(String.format(
                    "%s %s",
                    date.toString(), String.valueOf(tickets.size())
                ));
                tickets.forEach(out::println);
            });
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
