package ru.vityaman.tidb.data.json.field;

import java.util.function.Consumer;

/**
 * Field that can be set in future.
 * @param <T> type
 */
public class Lateinit<T> implements Field<T>{
    private final Consumer<T> verificator;
    private final Field<T> origin;

    /**
     * @param origin
     */
    public Lateinit(Field<T> origin) {
        this(origin, (v) -> {});
    }

    /**
     * @param origin
     * @param verification throws Exception if invalid
     */
    public Lateinit(Field<T> origin, Consumer<T> verification) {
        this.verificator = verification;
        this.origin = origin;
    }

    @Override
    public void set(T value) {
        verificator.accept(value);
        origin.set(value);
    }

    @Override
    public T value() {
        return origin.value();
    }
}
