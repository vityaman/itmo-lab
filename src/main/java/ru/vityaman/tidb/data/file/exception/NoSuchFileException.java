package ru.vityaman.tidb.data.file.exception;

/**
 * Error: file not found.
 */
public class NoSuchFileException extends FileAccessException {
    public NoSuchFileException(String message) {
        super(message);
    }

    public NoSuchFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
