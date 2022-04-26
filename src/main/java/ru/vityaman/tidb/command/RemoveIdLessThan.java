package ru.vityaman.tidb.command;

import java.util.List;
import java.util.stream.Collectors;

import ru.vityaman.tidb.data.resource.TicketResource;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.data.resource.exception.ResourceNotFoundException;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;
import static ru.vityaman.tidb.ui.out.ConsoleColor.*;

/**
 * Represents 'remove_id_less_than' command.
 */
public final class RemoveIdLessThan implements Executable {
    private final Out out;
    private final Tickets tickets;

    /**
     * @param tickets tickets collection to edit
     */
    public RemoveIdLessThan(Out out, Tickets tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute(int id) {
        out.print("Removed: { ");
        tickets.all().stream()
        .map(TicketResource::id)
        .filter((otherId) -> otherId < id)
        .collect(Collectors.toList())
        .forEach(ticketId -> {
            try {
                tickets.removeWithById(ticketId);
                out.print(GREEN.wrapped(ticketId.toString()) + " ");
            } catch (ResourceNotFoundException e) {
                out.print(RED.wrapped(ticketId.toString()) + " ");
            }
        });
        out.println("}");
        // TODO: code repetition @see Clear.java
    }

    @Override
    public void execute(List<Object> args) {
        execute((Integer) args.get(0));
    }
}
