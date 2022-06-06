package ru.vityaman.tidb.api.exception;

public class CapacityExceededException extends CollectionException {
    public CapacityExceededException(String message) {
        super(message);
    }
}
