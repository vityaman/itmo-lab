package ru.vityaman.vion.exception;

import ru.vityaman.vion.VionObject;

public class DeserializationException extends Exception {
    public DeserializationException(VionObject json, Throwable cause) {
        super(String.format(
                "Invalid vion %s: %s",
                json, cause.getMessage()
            ),
            cause
        );
    }

    public DeserializationException(VionObject json, String explanation) {
        super(String.format(
                "Invalid vion %s: %s",
                json, explanation
            )
        );
    }    
}
