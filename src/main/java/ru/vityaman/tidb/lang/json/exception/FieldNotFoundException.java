package ru.vityaman.tidb.lang.json.exception;

import ru.vityaman.tidb.lang.json.AbstractJsonObject;

public class FieldNotFoundException extends JsonExtractionException {
    public FieldNotFoundException(AbstractJsonObject json, String key) {
        super(json, String.format(
                "Filed with key '%s' not found", 
                key
            )
        );
    }
}
