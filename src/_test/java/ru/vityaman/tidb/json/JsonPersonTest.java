package ru.vityaman.tidb.json;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import ru.vityaman.tidb.data.json.JsonPerson;

public final class JsonPersonTest {
    private static final Map<String, Object> LOCATION_JSON = new HashMap<>(Map.of(
        "x", 10,
        "y", 20,
        "z", 30,
        "name", "FakePlace"
    ));

    @Test
    public void creationSuccess() {
        Map<String, Object> resource = new HashMap<>(Map.of(
            "height", 170,
            "passportId", "123456789",
            "location", LOCATION_JSON
        ));
        JsonPerson person = new JsonPerson(resource);
        assertEquals(170, person.height());
        assertEquals("123456789", person.passportId());
    }
}
