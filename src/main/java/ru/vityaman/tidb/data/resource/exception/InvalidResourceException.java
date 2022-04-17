package ru.vityaman.tidb.data.resource.exception;

/**
 * Error: resource is invalid.
 */
public class InvalidResourceException extends ResourceException {
    public InvalidResourceException(String message) {
        super(message);
    }

    public InvalidResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
