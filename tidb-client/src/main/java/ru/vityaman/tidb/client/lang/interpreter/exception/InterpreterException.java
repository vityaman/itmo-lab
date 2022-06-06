package ru.vityaman.tidb.client.lang.interpreter.exception;

/**
 * Error during command execution.
 */
public class InterpreterException extends Exception {
    public InterpreterException(String message) {
        super(message);
    }

    public InterpreterException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public InterpreterException(String message, Throwable cause) {
        super(message, cause);
    }
}
