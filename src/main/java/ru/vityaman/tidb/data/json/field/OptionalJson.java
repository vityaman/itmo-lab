package ru.vityaman.tidb.data.json.field;

import java.util.Map;

/**
 * Json wrapper of value that can be absent.
 * @param <T> type
 */
public final class OptionalJson<T> extends JsonField<T>
                                   implements OptionalField<T> {

    public OptionalJson(Map<String, Object> container, String name) {
        super(container, name);
    }

    public boolean isPresent() {
        return container.containsKey(name);
    }

    public void remove() {
        container.remove(name);
    }
}
