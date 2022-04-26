package ru.vityaman.tidb.data.json.field;

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
