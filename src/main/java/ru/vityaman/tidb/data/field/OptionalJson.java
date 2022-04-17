package ru.vityaman.tidb.data.field;

import java.util.Map;

/**
 * Json wrapper of value that can be absent.
 * @param <T> type
 */
public final class OptionalJson<T> extends Json<T>
                                   implements OptionalField<T> {

    public OptionalJson(String name, Map<String, Object> container) {
        super(name, container);
    }

    public boolean isPresent() {
        return container.containsKey(name);
    }

    public void remove() {
        container.remove(name);
    }
}
