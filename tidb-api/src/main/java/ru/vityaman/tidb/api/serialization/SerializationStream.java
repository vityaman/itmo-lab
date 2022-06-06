package ru.vityaman.tidb.api.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public final class SerializationStream extends ObjectOutputStream {
    private final ByteArrayOutputStream bytes;

    private SerializationStream(ByteArrayOutputStream stream) throws IOException {
        super(stream);
        bytes = stream;
    }

    public SerializationStream writeSerializable(Serializable object) {
        try {
            super.writeObject(object);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        return this;
    }

    public <T> SerializationStream writeUsingSerializer(
        T object, ObjectSerializer<T> serializer
    ) throws IOException {
        serializer.write(this, object);
        return this;
    }

    public SerializationStream writeInteger(int integer) throws IOException {
        super.write(integer);
        return this;
    }

    public void writeTo(byte[] buffer) {
        try { flush(); } catch (IOException e) { throw new AssertionError(); }
        byte[] bytes = this.bytes.toByteArray();
        System.arraycopy(bytes, 0, buffer, 0, bytes.length);
    }

    public void writeTo(ByteBuffer buffer) {
        try { flush(); } catch (IOException e) { throw new AssertionError(); }
        buffer.put(bytes());
    }

    public byte[] bytes() {
        try { flush(); } catch (IOException e) { throw new AssertionError(); }
        return bytes.toByteArray();
    }

    public static SerializationStream empty() {
        try {
            return new SerializationStream(new ByteArrayOutputStream());
        } catch (IOException ignored) {
            throw new AssertionError("Can't happen!");
        }
    }
}
