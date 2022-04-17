package ru.vityaman.tidb.command.exception;

/**
 * Error: script is called recursively.
 */
public class RecursiveCallException extends CommandException {
    public RecursiveCallException(String filepath) {
        super("Recursive call of script " + filepath);
    }
}
