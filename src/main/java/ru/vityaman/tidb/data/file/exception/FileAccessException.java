package ru.vityaman.tidb.data.file.exception;

/**
 * Error: can't access file.
 */
public class FileAccessException extends FileSystemException {
    public FileAccessException(String message) {
        super(message);
    }

    public FileAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
