package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.ui.printer.Printer;

import java.util.List;
import java.util.function.Supplier;

import static ru.vityaman.tidb.ui.printer.ConsoleColor.RED;

/**
 * Represents 'get' command.
 */
public final class GetById implements Executable {
    private final Printer out;
    private final Supplier<Tickets> tickets;

    /**
     * @param out where to print out
     * @param tickets tickets to edit
     */
    public GetById(Printer out, Supplier<Tickets> tickets) {
        this.out = out;
        this.tickets = tickets;
    }

    @Override
    public void execute(List<Object> args) {
        int id = ((Number) args.get(0)).intValue();
        out.println(RED.wrapped(tickets.get().ticketWithId(id).repr()));
    }
}
