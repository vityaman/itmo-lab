package ru.vityaman.tidb.api.interact;

public final class ServerError {
    public enum Type {
        EntryAlreadyExists, 
        CapacityExceeded,
        NoSuchEntryException;
    }

    private final Type type;
    private final String message;

    public ServerError(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Type type() {
        return type;
    }

    public String message() {
        return message;
    }
}
