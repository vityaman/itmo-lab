package ru.vityaman.vion.exception;

import ru.vityaman.vion.VionObject;

public class FieldExtractionException extends Exception {
    public FieldExtractionException(VionObject json, Throwable cause) {
        super(String.format(
                "Error in %s: %s",
                json, cause.getMessage()
            ),
            cause
        );
    }

    public FieldExtractionException(VionObject json, String explanation) {
        super(String.format(
                "Error in %s: %s",
                json, explanation
            )
        );
    }   
}
