package ru.vityaman.vion;

import java.util.Map;
import java.util.Set;

import ru.vityaman.vion.exception.FieldExtractionException;


public interface VionObject {
    void remove(String key);
    Set<String> keys();
    void put(String key, VionObject object);
    void put(String key, Number number);
    void put(String key, String string);
    void put(String key, Enum<?> constant);
    boolean contains(String key);
    VionObject object(String key) throws FieldExtractionException;
    Number number(String key) throws FieldExtractionException;
    String string(String key) throws FieldExtractionException;
    <T extends Enum<T>> T constant(String key, Class<T> enumType) 
    throws FieldExtractionException;
    Map<String, Object> toMap();
}
