package ru.vityaman.vion;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.vityaman.vion.exception.ConversionException;

public final class MapBasedVionObject extends AbstractVionObject {
    private final Map<String, Object> map;

    public MapBasedVionObject() {
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
            if (value instanceof VionObject) {
                result.put(key, ((VionObject) value).toMap());
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static VionObject fromMap(Map<String, Object> map) 
    throws ConversionException 
    {
        VionObject result = new MapBasedVionObject();
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
                    throw new ConversionException(e);
                }
            }
        }
        return result;
    }
}
