package ru.vityaman.tidb.command;

import java.util.List;

/**
 * Represents 'exit' command to stop app running.
 */
public final class Exit implements Executable {
    @Override
    public void execute(List<Object> args) {
        System.exit(0);
    }
}
