package ru.vityaman.tidb.data.json;

import java.util.Map;

/**
 * Basement for java.util.Map resource.
 */
public abstract class JsonResource {
    protected final Map<String, Object> json;

    public JsonResource(Map<String, Object> json) {
        this.json = json;
    }

    public JsonResource(Map<String, Object> json, Validator validator) {
        this.json = json;
        validator.requireValid(json);
    }

    @FunctionalInterface
    public interface Validator {
        void requireValid(Map<String, Object> json);
    }

    @Override
    public String toString() {
        return json.toString();
    }
}
