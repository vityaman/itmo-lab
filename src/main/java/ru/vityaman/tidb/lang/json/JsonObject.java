package ru.vityaman.tidb.lang.json;

import java.util.Map;
import java.util.Set;

import ru.vityaman.tidb.lang.json.exception.JsonExtractionException;

// JsonList support
public interface JsonObject {
    void remove(String key);
    Set<String> keys();
    void put(String key, JsonObject object);
    void put(String key, Number number);
    void put(String key, String string);
    void put(String key, Enum<?> constant);
    boolean contains(String key);
    JsonObject object(String key) throws JsonExtractionException;
    Number number(String key) throws JsonExtractionException;
    String string(String key) throws JsonExtractionException;
    <T extends Enum<T>> T constant(String key, Class<T> enumType) 
    throws JsonExtractionException;
    Map<String, Object> toMap();
}
