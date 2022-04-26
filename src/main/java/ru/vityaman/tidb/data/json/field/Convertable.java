package ru.vityaman.tidb.data.json.field;

import java.util.function.Function;

/**
 * @param <E> external type
 * @param <O> encapsulated type
 */
public final class Convertable<E, O> implements Field<E> {
    private final Field<O> origin;
    private final Function<E, O> convertingToOriginal;
    private final Function<O, E> convertingToExternal;

    /**
     * @param origin
     * @param toOriginal cast function E -> O
     * @param toExternal cast function O -> E
     */
    public Convertable(Field<O> origin,
                            Function<E, O> toOriginal,
                            Function<O, E> toExternal) {
        this.origin = origin;
        this.convertingToOriginal = toOriginal;
        this.convertingToExternal = toExternal;
    }

    @Override
    public void set(E value) {
        origin.set(convertingToOriginal.apply(value));
    }

    @Override
    public E value() {
        return convertingToExternal.apply(origin.value());
    }
}
