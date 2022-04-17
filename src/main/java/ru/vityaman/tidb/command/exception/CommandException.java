package ru.vityaman.tidb.command.exception;

/**
 * Error during command execution.
 */
public class CommandException extends RuntimeException {
    public CommandException(String message) {
        super(message);
    }
}
