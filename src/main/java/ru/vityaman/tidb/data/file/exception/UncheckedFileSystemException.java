package ru.vityaman.tidb.data.file.exception;

import java.nio.file.Path;

public class UncheckedFileSystemException extends RuntimeException {
    public UncheckedFileSystemException(String message) {
        super(message);
    }

    public UncheckedFileSystemException(Throwable cause) {
        super(cause);
    }

    public UncheckedFileSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UncheckedFileSystemException whileWorkingWith(
                    Path path, Exception cause) {
        return new UncheckedFileSystemException(
            String.format(
                "Error while working with file %s: %s",
                path, cause.getMessage()
            ),
            cause
        );
    }
}
