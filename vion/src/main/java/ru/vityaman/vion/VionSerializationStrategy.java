package ru.vityaman.vion;

import ru.vityaman.vion.exception.DeserializationException;

public interface VionSerializationStrategy<T> {
    VionObject serialized(T object);
    T deserialized(VionObject json) throws DeserializationException;
}
