package ru.vityaman.tidb.lang.json.exception;

import ru.vityaman.tidb.lang.json.JsonObject;

public class JsonDeserializationException extends Exception {
    public JsonDeserializationException(JsonObject json, Throwable cause) {
        super(String.format(
                "Invalid json %s: %s",
                json, cause.getMessage()
            ),
            cause
        );
    }

    public JsonDeserializationException(JsonObject json, String explanation) {
        super(String.format(
                "Invalid json %s: %s",
                json, explanation
            )
        );
    }    
}
