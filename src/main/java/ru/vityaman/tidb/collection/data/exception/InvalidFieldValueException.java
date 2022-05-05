package ru.vityaman.tidb.collection.data.exception;

public class InvalidFieldValueException extends Exception {

    public InvalidFieldValueException(String message) {
        super(message);
    }

    public InvalidFieldValueException(Throwable cause) {
        super(cause);
    }

    public InvalidFieldValueException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
