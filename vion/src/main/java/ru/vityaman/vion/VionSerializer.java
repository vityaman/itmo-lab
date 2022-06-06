package ru.vityaman.vion;


import ru.vityaman.vion.exception.DeserializationException;

public interface VionSerializer<T> {
    VionObject serialize(T object);
    T deserialize(VionObject json) throws DeserializationException;
}
