package ru.vityaman.tidb.lang.json.exception;

import ru.vityaman.tidb.lang.json.JsonObject;

public class FieldTypeMismatchException extends JsonExtractionException {
    public FieldTypeMismatchException(
        JsonObject json, 
        String key, 
        Class<?> expected,
        Class<?> found
    ) {
        super(
            json, String.format(
                "Type mismatch for field '%s': %s expected, found: %s", 
                key, expected, found
            )
        );
    }
}
