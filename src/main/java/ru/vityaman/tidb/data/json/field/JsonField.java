package ru.vityaman.tidb.data.json.field;

import java.util.Map;

/**
 * Wrapper of java.util.Map to
 * convenient access to its members.
 * @param <T> type
 */
public class JsonField<T> implements Field<T> {
    protected final String name;
    protected final Map<String, Object> container;

    public JsonField(Map<String, Object> container, String name) {
        this.name = name;
        this.container = container;
    }

    @Override
    public void set(T value) {
        container.put(name, value);
    }

    @Override
    public T value() throws InvalidJsonException {
        if (!container.containsKey(name)) {
            throw new InvalidJsonException(String.format(
                "Json does not contain field %s", name));
        }

        Object value = container.get(name);
        try {
            return (T) value;
        } catch (ClassCastException e) {
            throw new InvalidJsonException(
                "Field " + name + " has incompatible type", e);
        }
    }

    public static final class InvalidJsonException extends RuntimeException {
        public InvalidJsonException(String message) {
            super(message);
        }

        public InvalidJsonException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidJsonException(Throwable cause) {
            super("Invalid json provided", cause);
        }
    }
}
