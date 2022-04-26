package ru.vityaman.tidb.lang.interpreter.exception;

/**
 * Error: script is called recursively.
 */
public class RecursiveCallException extends ExecutionException {
    public RecursiveCallException(String filepath) {
        super("Recursive call of script " + filepath);
    }
}
