package ru.vityaman.tidb.data.file.exception;

public class FileSystemException extends Exception {
    public FileSystemException(String message) {
        super(message);
    }

    public FileSystemException(Throwable cause) {
        super(cause);
    }

    public FileSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public final UncheckedFileSystemException unchecked() {
        return new UncheckedFileSystemException(getMessage(), getCause());
    }
}
