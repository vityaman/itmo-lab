package ru.vityaman.tidb.lang.interpreter.exception;

/**
 * Error: there are no such command in interpreter.
 */
public class NoSuchInstructionException extends InterpreterException {
    public NoSuchInstructionException(String keyword) {
        super("No such command: " + keyword);
    }
}
