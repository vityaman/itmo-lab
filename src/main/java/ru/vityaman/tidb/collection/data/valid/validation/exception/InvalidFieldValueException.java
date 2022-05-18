package ru.vityaman.tidb.collection.data.valid.validation.exception;

public class InvalidFieldValueException extends ValidationException {

    public InvalidFieldValueException(String fieldName, String asIt) {
        this(String.format(
            "Field '%s' is invalid as it %s",
            fieldName, asIt
        ));
    }

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
