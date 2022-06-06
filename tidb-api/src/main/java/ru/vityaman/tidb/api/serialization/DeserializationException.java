package ru.vityaman.tidb.api.serialization;

public class DeserializationException extends Exception {

    public DeserializationException(Throwable cause) {
        super(cause);
    }

    public DeserializationException(String message) {
        super(message);
    }

    public DeserializationException(String message, Throwable cause) {
        super("Invalid object given: " + cause.getMessage(), cause);
    }
    
}
