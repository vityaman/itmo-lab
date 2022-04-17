package ru.vityaman.tidb.external;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public final class OrgJsonTest {

    @Test
    public void creation() {
        JSONObject json = new JSONObject();
        json.put("int", 1);
        json.put("float", 1F);
        json.put("string", "string");
        json.put("bool", true);
        json.put("integer", Integer.valueOf(1));

        assertEquals(json.getInt("int"), 1);
        assertEquals(json.getFloat("float"), 1F, 0.01);
        assertEquals(json.getString("string"), "string");
        assertEquals(json.getBoolean("bool"), true);
        assertEquals(json.getInt("integer"), 1);
    }

    @Test
    public void simpleMapConversion() {
        JSONObject json = new JSONObject();
        json.put("int", 1);
        json.put("float", 1F);
        json.put("string", "string");
        json.put("bool", true);
        json.put("integer", Integer.valueOf(1));

        HashMap<String, Object> actual = new HashMap<>(json.toMap());
        HashMap<String, Object> expected = new HashMap<>(Map.of(
            "int", 1,
            "float", 1F,
            "string", "string",
            "bool", true,
            "integer", Integer.valueOf(1)
        ));

        assertEquals(expected, actual);
    }

    @Test
    public void complexMapConversion() {
        Map<String, Object> structure = Map.of(
            "name", "default",
            "innner", Map.of(
                "1", 1,
                "2", 2,
                "3", 3
            ),
            "1", 1,
            "123", List.of(1, 2, 3)
        );

        JSONObject json = new JSONObject(structure);

        HashMap<String, Object> actual = new HashMap<>(json.toMap());
        HashMap<String, Object> expected = new HashMap<>(structure);

        assertEquals(expected, actual);
    }
}
