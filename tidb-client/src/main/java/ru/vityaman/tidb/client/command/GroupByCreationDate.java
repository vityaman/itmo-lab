package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.ui.out.Out;

import java.io.IOException;
import java.util.List;


/**
 * Represents 'group_by_creation_date' command.
 */
public final class GroupByCreationDate implements Executable {
    private final Out out;
    private final RemoteTicketCollection tickets;

    /**
     * @param out where to print out.
     * @param tickets collection to edit.
     */
    public GroupByCreationDate(Out out, RemoteTicketCollection tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute() throws IOException {
        tickets.entriesGroupedByCreationDate()
            .forEach((date, tickets) -> {
                out.println(String.format(
                    "| date: %s, count: %s",
                    date, tickets.size()
                ));
                tickets.forEach(out::println);
            });
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute();
        } catch (IOException e) {
            throw new ExecutionException(e);
        }
    }
}
