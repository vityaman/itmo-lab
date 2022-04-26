package ru.vityaman.tidb.data.json.exception;

import java.util.Map;

public class InvalidJsonResourceException extends RuntimeException {
    public InvalidJsonResourceException(Map<String, Object> json, Throwable cause) {
        super(
            String.format(
                "Invalid resource %s as %s",
                json, cause
            ),
            cause
        );
    }
}
