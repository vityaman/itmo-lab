package ru.vityaman.tidb.json;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ru.vityaman.tidb.data.json.JsonTicket;
import ru.vityaman.tidb.data.json.JsonTicketEntry;
import ru.vityaman.tidb.data.json.StringDate;
import ru.vityaman.tidb.data.model.TicketType;

public final class JsonTicketTest {
    private static final double PRECISION = 0.0001;

    @Test
    public void creationSuccess() {
        Map<String, Object> resource = new HashMap<>(Map.of(
            "id", 1,
            "name", "Programming Lecture #1",
            "coordinates", new HashMap<>(Map.of(
                "x", 1,
                "y", 2
            )),
            "creationDate", "01.01.2022 00:00:00",
            "type", "VIP",
            "person", new HashMap<>(Map.of(
                "height", 170,
                "passportId", "123456789",
                "location", new HashMap<>(Map.of(
                    "x", 10,
                    "y", 12.2,
                    "z", 7,
                    "name", "Home"
                ))
            ))
        ));
        JsonTicketEntry ticket = new JsonTicketEntry(resource);
        assertEquals(1, ticket.id());
        assertEquals("Programming Lecture #1", ticket.name());
        assertEquals(1, ticket.coordinates().x(), PRECISION);
        assertEquals(2, ticket.coordinates().y(), PRECISION);
        assertEquals(new StringDate("01.01.2022 00:00:00").asDate(),
                     ticket.creationDate());
        assertEquals(TicketType.VIP, ticket.type());
        assertEquals(170, ticket.person().height());
        assertEquals("123456789", ticket.person().passportId());
        assertEquals(10, ticket.person().location().x(), PRECISION);
        assertEquals(12.2, ticket.person().location().y(), PRECISION);
        assertEquals(7, ticket.person().location().z(), PRECISION);
        assertEquals("Home", ticket.person().location().name());
    }
}
