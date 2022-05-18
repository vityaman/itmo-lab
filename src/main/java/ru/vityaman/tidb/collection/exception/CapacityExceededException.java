package ru.vityaman.tidb.collection.exception;

public class CapacityExceededException extends CollectionException {
    public CapacityExceededException(String message) {
        super(message);
    }
}
