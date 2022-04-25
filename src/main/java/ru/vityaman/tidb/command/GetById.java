package ru.vityaman.tidb.command;

import static ru.vityaman.tidb.ui.out.ConsoleColor.RED;

import java.util.List;
import java.util.function.Supplier;

import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents 'get' command.
 */
public final class GetById implements Executable {
    private final Out out;
    private final Supplier<Tickets> tickets;

    /**
     * @param out where to print out
     * @param tickets tickets to edit
     */
    public GetById(Out out, Supplier<Tickets> tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute(int id) {
        out.println(
            tickets.get()
                .ticketWithId(id)
                .representation()
        );
    }

    @Override
    public void execute(List<Object> args) {
        // TODO: (10.5).intValue()
        execute(((Number) args.get(0)).intValue());
    }
}
