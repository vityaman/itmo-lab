package ru.vityaman.vion;

import java.util.Set;

import ru.vityaman.vion.exception.FieldExtractionException;
import ru.vityaman.vion.exception.FieldNotFoundException;
import ru.vityaman.vion.exception.FieldTypeMismatchException;

abstract class AbstractVionObject implements VionObject {
    @Override
    public abstract void remove(String key);

    @Override
    public abstract Set<String> keys();

    protected abstract Object nullableObject(String key);
    
    protected abstract void putObject(String key, Object value);

    @Override
    public void put(String key, VionObject object) {
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
    public VionObject object(String key) throws FieldExtractionException {
        return value(VionObject.class, key);
    }

    @Override
    public Number number(String key) throws FieldExtractionException {
        return value(Number.class, key);
    }

    @Override
    public String string(String key) throws FieldExtractionException {
        return value(String.class, key);
    }

    @Override
    public <T extends Enum<T>> T constant(String key, Class<T> enumType) 
    throws FieldExtractionException {
        try {
            return Enum.valueOf(enumType, string(key));
        } catch (IllegalArgumentException e) {
            throw new FieldExtractionException(this, e);
        }
    }

    private <T> T value(Class<T> type, String key) 
    throws FieldExtractionException {
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