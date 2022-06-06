package ru.vityaman.tidb.api.interact.client;

import java.io.IOException;

public class BadResponseException extends IOException {

    public BadResponseException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public BadResponseException(String message) {
        super(message);
    }

    public BadResponseException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
