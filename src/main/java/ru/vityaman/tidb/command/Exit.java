package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.lang.interpreter.Executable;

/**
 * Represents 'exit' command to stop app running.
 */
public final class Exit implements Executable {
    @Override
    public void execute(List<Object> args) {
        System.exit(0);
    }
}
