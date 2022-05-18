package ru.vityaman.tidb.collection.data.valid.validation;

import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;

public interface Validator<T> {
    public void ensureValid(T object) throws ValidationException;

    default void ensureNotNull(String fieldName, Object object) {
        if (object == null) {
            Exception e = new InvalidFieldValueException(
                fieldName, "is not null"
            );
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
