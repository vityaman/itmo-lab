package ru.vityaman.tidb.data.resource.exception;

/**
 * Error: something wrong with resource.
 */
public class ResourceException extends RuntimeException {
    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
