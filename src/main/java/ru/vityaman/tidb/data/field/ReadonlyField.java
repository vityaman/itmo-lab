package ru.vityaman.tidb.data.field;

/**
 * Field view.
 * @param <T> type
 */
public interface ReadonlyField<T> {
    /**
     * @return value
     */
    T value();
}
