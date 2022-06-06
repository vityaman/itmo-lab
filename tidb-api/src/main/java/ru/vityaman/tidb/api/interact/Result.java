package ru.vityaman.tidb.api.interact;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

public final class Result implements Serializable {
    private final ServerError error;
    private final byte[] payload;

    public Result(ServerError error, byte[] payload) {
        this.error = error;
        this.payload = Arrays.copyOf(payload, payload.length);
    }

    public Result(byte[] payload) {
        this(null, payload);
    }

    public Optional<ServerError> error() {
        return Optional.ofNullable(error);
    }

    public ObjectInputStream payload() throws IOException {
        return new ObjectInputStream(new ByteArrayInputStream(payload));
    }

    public static Result deserialized(ObjectInputStream stream) 
    throws IOException {
        try {
            return (Result) stream.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new IOException("Invalid serialised form of Result");
        } 
    }

    @Override
    public String toString() {
        return String.format(
            "Result[error: %s, payload: %s]",
            error, Arrays.toString(payload)
        );
    }
}
