package ru.vityaman.tidb.data.model.exception;

/**
 * Error: provided value is invalid.
 */
public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
}
