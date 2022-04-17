package ru.vityaman.tidb.command.exception;

/**
 * Error: argument provided to function is invalid.
 */
public class InvalidArgumentException extends CommandException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
