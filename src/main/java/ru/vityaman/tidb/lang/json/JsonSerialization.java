package ru.vityaman.tidb.lang.json;

import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;


public interface JsonSerialization<T> {
    JsonObject serialized(T object);
    T deserialized(JsonObject json) throws JsonDeserializationException;
}
