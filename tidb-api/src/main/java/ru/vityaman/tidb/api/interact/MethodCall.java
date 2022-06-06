package ru.vityaman.tidb.api.interact;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;

import ru.vityaman.tidb.api.method.Method;
import ru.vityaman.tidb.api.serialization.SerializationStream;

public final class MethodCall implements Serializable {
    private final Method method;
    private final byte[] payload;

    public MethodCall(Method method, byte[] payload) {
        this.method = method;
        this.payload = Arrays.copyOf(payload, payload.length);
    }

    public MethodCall(Method method, SerializationStream payload) {
        this(method, payload.bytes());
    }

    public Method method() {
        return method;
    }

    public ObjectInputStream payload() throws IOException {
        return new ObjectInputStream(new ByteArrayInputStream(payload));
    }

    @Override
    public String toString() {
        return String.format(
            "%s(%s)", 
            method, Arrays.toString(payload)
        );
    }
}
