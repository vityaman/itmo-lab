package ru.vityaman.tidb.data.file.exception;

public class InvalidFileStructureException extends RuntimeException {
    public InvalidFileStructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
