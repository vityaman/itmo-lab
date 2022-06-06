package ru.vityaman.tidb.client.file.exception;

import java.io.IOException;

public class InvalidFileContentException extends IOException {
    public InvalidFileContentException(String message) {
        super(message);
    }

    public InvalidFileContentException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public InvalidFileContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
