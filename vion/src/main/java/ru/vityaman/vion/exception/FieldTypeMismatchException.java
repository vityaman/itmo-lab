package ru.vityaman.vion.exception;

import ru.vityaman.vion.VionObject;

public class FieldTypeMismatchException extends FieldExtractionException {
    public FieldTypeMismatchException(
        VionObject json, String key, Class<?> expected, Class<?> found
    ) {
        super(
            json, String.format(
                "Type mismatch for field '%s': %s expected, found: %s", 
                key, expected, found
            )
        );
    }
}
