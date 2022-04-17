package ru.vityaman.tidb.command;

import ru.vityaman.tidb.ui.printer.Printer;

import java.util.List;

import static ru.vityaman.tidb.ui.printer.ConsoleColor.GREEN;

/**
 * Represents a 'pwd' command.
 */
public final class Pwd implements Executable {
    private final Printer out;

    /**
     * @param out where to print out
     */
    public Pwd(Printer out) {
        this.out = out;
    }

    @Override
    public void execute(List<Object> args) {
        out.println(GREEN.wrapped(System.getProperty("user.dir")));
    }
}
