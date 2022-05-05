package ru.vityaman.tidb.file.exception;

public class InvalidFileContentException extends Exception {
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
