package ru.vityaman.tidb.data.json.field;

import java.util.function.Consumer;

/**
 * Field with verification.
 * @param <T> type
 */
public final class Verified<T> extends Lateinit<T> {
    public Verified(Field<T> origin) {
        super(origin);
    }

    public Verified(Field<T> origin, Consumer<T> verification) {
        super(origin, verification);
        verification.accept(this.value());
    }
}
