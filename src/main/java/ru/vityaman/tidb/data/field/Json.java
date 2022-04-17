package ru.vityaman.tidb.data.field;

import java.util.Map;

/**
 * Wrapper of java.util.Map to
 * convenient access to its members.
 * @param <T> type
 */
public class Json<T> implements Field<T> {
    protected final String name;
    protected final Map<String, Object> container;

    public Json(String name, Map<String, Object> container) {
        this.name = name;
        this.container = container;
    }

    @Override
    public void set(T value) {
        container.put(name, value);
    }

    @Override
    public T value() throws InvalidJsonException {
        try {
            return (T) container.computeIfAbsent(name, (k) -> {
                throw new InvalidJsonException(
                    "Json does not contain field " + name);
            });
        } catch (ClassCastException e) {
            throw new InvalidJsonException(
                "Field " + name + " has incompatible type", e);
        } catch (UnsupportedOperationException e) {
            throw new IllegalStateException(
                "Map container must be mutable", e);
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
