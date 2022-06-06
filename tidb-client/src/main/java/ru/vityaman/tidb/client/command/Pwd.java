package ru.vityaman.tidb.client.command;


import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.ui.out.Out;

import java.util.List;

import static ru.vityaman.tidb.client.ui.out.ConsoleColor.GREEN;


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
