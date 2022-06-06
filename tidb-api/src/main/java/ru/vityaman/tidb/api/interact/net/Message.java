package ru.vityaman.tidb.api.interact.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

public abstract class Message {
    private final ObjectInputStream payload;

    Message(byte[] payload) {
        try {
            this.payload = new ObjectInputStream(
                new ByteArrayInputStream(
                    Arrays.copyOf(payload, payload.length)
                )
            );
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public final ObjectInputStream payload() {
        return payload;
    }
}
