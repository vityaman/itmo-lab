package ru.vityaman.tidb.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ru.vityaman.tidb.data.field.Json.InvalidJsonException;
import ru.vityaman.tidb.data.json.JsonLocation;

public final class JsonLocationTest {
    private static final double PRECISION = 0.0001;

    @Test
    public void creationSuccess() {
        Map<String, Object> resource = new HashMap<>(Map.of(
            "x", 12,
            "y", 34,
            "z", 23,
            "name", "Place"
        ));
        JsonLocation location = new JsonLocation(resource);
        assertEquals(12, location.x(), PRECISION);
        assertEquals(34, location.y(), PRECISION);
        assertEquals(23, location.z(), PRECISION);
        assertEquals("Place", location.name());
    }

    @Test
    public void resourceMutability() {
        Map<String, Object> resource = new HashMap<>(Map.of(
            "x", 1,
            "y", 78,
            "z", 8,
            "name", "City"
        ));
        JsonLocation location = new JsonLocation(resource);

        resource.put("x", 1.05);
        resource.put("y", 91);
        resource.put("z", 721.21);
        resource.put("name", "Home");
        assertEquals(1.05, location.x(), PRECISION);
        assertEquals(91, location.y(), PRECISION);
        assertEquals(721.21, location.z(), PRECISION);
        assertEquals("Home", location.name());

        location.setX(0);
        location.setY(1);
        location.setZ(2);
        location.setName("Russia");
        assertEquals(0, (float) resource.get("x"), PRECISION);
        assertEquals(1, (double) resource.get("y"), PRECISION);
        assertEquals(2, (float) resource.get("z"), PRECISION);
        assertEquals("Russia", resource.get("name"));
    }

    @Test
    public void creationFailsWithoutRequiredArguments() {
        assertThrows(InvalidJsonException.class, () -> {
            new JsonLocation(
                new HashMap<>(Map.of(
                    "x", 32,
                    "z", 5
            )));
        });
    }

    @Test
    public void setFailsBecauseOfImmutableResource() {
        Map<String, Object> resource = Map.of(
            "x", 10,
            "y", 40,
            "z", 1,
            "name", "Immutable name"
        );
        assertThrows(InvalidJsonException.class, () -> {
            new JsonLocation(resource);
        });
    }
}
