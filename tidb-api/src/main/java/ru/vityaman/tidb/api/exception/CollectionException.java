package ru.vityaman.tidb.api.exception;

public class CollectionException extends Exception {
    public CollectionException(String message) {
        super(message);
    }

    public CollectionException(Throwable cause) {
        super(cause);
    }

    public CollectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
