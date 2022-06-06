package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.client.lang.interpreter.Executable;

import java.util.List;


/**
 * Represents 'exit' command to stop app running.
 */
public final class Exit implements Executable {
    public void execute() {
        System.exit(0);
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
