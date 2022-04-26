package ru.vityaman.tidb.data.json.field;

/**
 * Represents a nullable field.
 * @param <T> type
 */
public interface OptionalField<T> extends Field<T> {
    /**
     * @return true if field is nonnull.
     */
    boolean isPresent();
    /**
     * Sets field to null.
     */
    void remove();
}
