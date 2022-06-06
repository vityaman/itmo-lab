package ru.vityaman.tidb.api.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface ObjectSerializer<T> {
    T read(ObjectInputStream stream) throws IOException, DeserializationException;
    void write(ObjectOutputStream stream, T object) throws IOException;

    @FunctionalInterface
    interface ObjectBuilder<T> {
        T build() throws Exception;
    }

    static <T> T deserialized(ObjectBuilder<T> builder) 
    throws DeserializationException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new DeserializationException(e);
        }
    }
}
