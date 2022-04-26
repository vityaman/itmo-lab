package ru.vityaman.tidb.data.json.field;

import java.util.function.Consumer;

/**
 * Nullable field that need verification.
 * @param <T> type
 */
public final class OptionalVerified<T> implements OptionalField<T> {
    private final OptionalField<T> origin;
    private final Consumer<T> verificator;

    public OptionalVerified(OptionalField<T> origin, Consumer<T> verificator) {
        this.origin = origin;
        this.verificator = verificator;

        if (isPresent()) {
            verificator.accept(origin.value());
        }
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

    @Override
    public boolean isPresent() {
        return origin.isPresent();
    }

    @Override
    public void remove() {
        origin.remove();
    }
}
