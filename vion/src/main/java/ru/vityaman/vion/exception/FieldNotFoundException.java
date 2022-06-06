package ru.vityaman.vion.exception;

import ru.vityaman.vion.VionObject;

public class FieldNotFoundException extends FieldExtractionException {
    public FieldNotFoundException(VionObject json, String key) {
        super(json, String.format(
            "Filed with key '%s' not found", 
            key
        ));
    }
}
