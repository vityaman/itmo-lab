package ru.vityaman.tidb.data.file.exception;

/**
 * Error: some file system error.
 */
public class FileSystemException extends RuntimeException {
    public FileSystemException(String message) {
        super(message);
    }

    public FileSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
