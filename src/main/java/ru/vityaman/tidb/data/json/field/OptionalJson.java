package ru.vityaman.tidb.data.json.field;

import java.util.Map;

import ru.vityaman.tidb.data.field.OptionalField;

/**
 * Json wrapper of value that can be absent.
 * @param <T> type
 */
public final class OptionalJson<T> extends JsonField<T>
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
