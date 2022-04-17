package ru.vityaman.tidb.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ru.vityaman.tidb.data.field.Json.InvalidJsonException;
import ru.vityaman.tidb.data.json.JsonCoordinates;

public final class JsonCoordinatesTest {
    private static final double PRECISION = 0.0001;

    @Test
    public void creationSuccess() {
        Map<String, Object> resource = new HashMap<>(Map.of(
            "x", 12,
            "y", 34
        ));
        JsonCoordinates coordinates = new JsonCoordinates(resource);
        assertEquals(12, coordinates.x(), PRECISION);
        assertEquals(34, coordinates.y(), PRECISION);
    }

    @Test
    public void resourceMutability() {
        Map<String, Object> resource = new HashMap<>(Map.of(
            "x", 126,
            "y", 13
        ));
        JsonCoordinates coordinates = new JsonCoordinates(resource);

        resource.put("x", 10);
        resource.put("y", 78);
        assertEquals(10, coordinates.x(), PRECISION);
        assertEquals(78, coordinates.y(), PRECISION);

        coordinates.setX(8);
        coordinates.setY(3);
        assertEquals(8, (double) resource.get("x"), PRECISION);
        assertEquals(3, (double) resource.get("y"), PRECISION);
    }

    @Test
    public void creationFailsBecauseOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> {
            new JsonCoordinates(
                new HashMap<>(Map.of(
                    "x", 54,
                    "y", 763
            )));
        });
    }

    @Test
    public void creationFailsWithoutRequiredArguments() {
        assertThrows(InvalidJsonException.class, () -> {
            new JsonCoordinates(
                new HashMap<>(Map.of(
                    "y", 750
            )));
        });
    }

    @Test
    public void setFailsBecauseOfImmutableResource() {
        Map<String, Object> resource = Map.of(
            "x", 10,
            "y", 40
        );
        assertThrows(InvalidJsonException.class, () -> {
            new JsonCoordinates(resource);
        });
    }
}
