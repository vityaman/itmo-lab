package ru.vityaman.tidb.lang.json;

import java.util.Set;

import ru.vityaman.tidb.lang.json.exception.FieldNotFoundException;
import ru.vityaman.tidb.lang.json.exception.FieldTypeMismatchException;
import ru.vityaman.tidb.lang.json.exception.JsonExtractionException;

public abstract class AbstractJsonObject implements JsonObject {
    @Override
    public abstract void remove(String key);

    @Override
    public abstract Set<String> keys();

    protected abstract Object nullableObject(String key);
    
    protected abstract void putObject(String key, Object value);

    @Override
    public void put(String key, JsonObject object) {
        putObject(key, object);
    }

    @Override
    public void put(String key, Number number) {
        putObject(key, number);
    }

    @Override
    public void put(String key, String string) {
        putObject(key, string);
    }

    @Override
    public void put(String key, Enum<?> constant) {
        putObject(key, constant);
    }

    @Override
    public boolean contains(String key) {
        return nullableObject(key) != null;
    }

    @Override
    public JsonObject object(String key) throws JsonExtractionException {
        return value(JsonObject.class, key);
    }

    @Override
    public Number number(String key) throws JsonExtractionException {
        return value(Number.class, key);
    }

    @Override
    public String string(String key) throws JsonExtractionException {
        return value(String.class, key);
    }

    @Override
    public <T extends Enum<T>> T constant(String key, Class<T> enumType) 
    throws JsonExtractionException {
        try {
            return Enum.valueOf(enumType, string(key));
        } catch (IllegalArgumentException e) {
            throw new JsonExtractionException(this, e);
        }
    }

    private <T> T value(Class<T> type, String key) 
    throws JsonExtractionException {
        Object object = nullableObject(key);
        if (object == null) {
            throw new FieldNotFoundException(this, key);
        }
        if (!type.isAssignableFrom(object.getClass())) {
            throw new FieldTypeMismatchException(
                this, key, type, object.getClass()
            );
        }
        return type.cast(object);
    }
}