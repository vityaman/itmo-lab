package ru.vityaman.tidb.collection.data.valid.validation.exception;

public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
