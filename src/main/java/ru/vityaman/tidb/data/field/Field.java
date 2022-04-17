package ru.vityaman.tidb.data.field;

/**
 * Represents mutable field.
 * @param <T> type
 */
public interface Field<T> extends ReadonlyField<T> {
    /**
     * Sets filed to provided value.
     * @param value to set
     */
    void set(T value);
}
