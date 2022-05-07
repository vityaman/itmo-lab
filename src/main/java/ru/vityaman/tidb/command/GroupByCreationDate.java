package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents 'group_by_creation_date' command.
 */
public final class GroupByCreationDate implements Executable {
    private final Out out;
    private final TicketCollection tickets;

    /**
     * @param out where to print out.
     * @param tickets collection to edit.
     */
    public GroupByCreationDate(Out out, TicketCollection tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() {
        tickets.entriesGroupedByCreationDate()
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
