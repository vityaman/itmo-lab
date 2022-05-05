package ru.vityaman.tidb.lang.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.vityaman.tidb.lang.json.exception.JsonConversionException;

public final class MapJsonObject extends AbstractJsonObject {
    private final Map<String, Object> map;

    public MapJsonObject() {
        map = new HashMap<>();
    }

    @Override
    public void putObject(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public Object nullableObject(String key) {
        return map.get(key);
    }

    @Override
    public Set<String> keys() {
        return map.keySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonObject) {
                result.put(key, ((JsonObject) value).toMap());
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    public static JsonObject fromMap(Map<String, Object> map) 
    throws JsonConversionException {
        JsonObject result = new MapJsonObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                result.put(key, (String) value);
            } else if (value instanceof Number) {
                result.put(key, (Number) value);
            } else if (value instanceof Enum) {
                result.put(key, (Enum<?>) value);
            } else if (value instanceof Map) {
                try {
                    result.put(
                        key,
                        fromMap(
                            (Map<String, Object>) value
                        )
                    );
                } catch (ClassCastException e) {
                    throw new JsonConversionException(e);
                }
            }
        }
        return result;
    }
}
