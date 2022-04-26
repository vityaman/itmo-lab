package ru.vityaman.tidb.lang.interpreter.exception;

/**
 * Error: argument provided to function is invalid.
 */
public class InvalidArgumentException extends InterpreterException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
