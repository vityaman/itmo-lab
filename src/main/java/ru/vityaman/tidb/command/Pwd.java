package ru.vityaman.tidb.command;

import static ru.vityaman.tidb.ui.out.ConsoleColor.GREEN;

import java.util.List;

import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'pwd' command.
 */
public final class Pwd implements Executable {
    private final Out out;

    /**
     * @param out where to print out
     */
    public Pwd(Out out) {
        this.out = out;
    }

    private void execute() {
        out.println(GREEN.wrapped(System.getProperty("user.dir")));
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
