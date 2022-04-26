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
 * Represents a command 'clear' to
 * delete all tickets from the collection.
 */
public final class Clear implements Executable {
    private final Out out;
    private final Tickets tickets;

    /**
     * @param tickets source of collection
     */
    public Clear(Out out, Tickets tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    public void execute() {
        out.print("Removed: { ");
        tickets.all().stream()
        .map(TicketResource::id)
        .collect(Collectors.toList())
        .forEach(id -> {
            try {
                tickets.removeWithById(id);
                out.print(GREEN.wrapped(id.toString()) + " ");
            } catch (ResourceNotFoundException e) {
                out.print(RED.wrapped(id.toString()) + " ");
            }
        });
        out.println("}");
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
