package ru.vityaman.tidb.command;

import static ru.vityaman.tidb.ui.out.ConsoleColor.RED;

import java.util.List;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents 'get' command.
 */
public final class GetById implements Executable {
    private final Out out;
    private final Tickets tickets;

    /**
     * @param out where to print out
     * @param tickets tickets to edit
     */
    public GetById(Out out, Tickets tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    private void execute(int id) {
        out.println(RED.wrapped(tickets.ticketWithId(id).repr()));
    }

    @Override
    public void execute(List<Object> args) {
        // TODO: (10.5).intValue()
        execute(((Number) args.get(0)).intValue());
    }
}
