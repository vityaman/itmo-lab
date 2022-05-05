package ru.vityaman.tidb.lang.json.exception;

import ru.vityaman.tidb.lang.json.JsonObject;

public class JsonExtractionException extends Exception {
    public JsonExtractionException(JsonObject json, Throwable cause) {
        super(String.format(
                "Error in %s: %s",
                json, cause.getMessage()
            ),
            cause
        );
    }

    public JsonExtractionException(JsonObject json, String explanation) {
        super(String.format(
                "Error in %s: %s",
                json, explanation
            )
        );
    }   
}
