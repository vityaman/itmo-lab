package ru.vityaman.tidb.command.exception;

/**
 * Error: there are no such command in interpreter.
 */
public class NoSuchCommandException extends CommandException {
    public NoSuchCommandException(String keyword) {
        super("No such command: " + keyword);
    }
}
