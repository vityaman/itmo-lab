package ru.vityaman.tidb.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import ru.vityaman.tidb.data.json.JsonTickets;
import ru.vityaman.tidb.data.model.Coordinates;
import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.data.model.Person;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.model.TicketType;
import ru.vityaman.tidb.data.resource.TicketResource;

public final class JsonTicketsTest {
    @Test
    public void creation() {
        final Map<String, Object> a = new HashMap<>(Map.of(
            "id", 126,
            "name", "Programming Lecture #1",
            "coordinates", new HashMap<String, Object>(Map.of(
                "x", 100,
                "y", 126
            )),
            "creationDate", "10.03.2022 11:40:00",
            "type", "VIP",
            "person", new HashMap<>(Map.of(
                "height", 170,
                "passportId", "GFgfgHg78hG121ksSj23kOSs",
                "location", new HashMap<>(Map.of(
                    "x", 67,
                    "y", 124,
                    "z", 90,
                    "name", "ITMO University"
                ))
            ))
        ));
        final Map<String, Object> b = new HashMap<>(Map.of(
            "id", 101,
            "name", "VK Fest 2022",
            "coordinates", new HashMap<String, Object>(Map.of(
                "x", 39,
                "y", 67
            )),
            "creationDate", "23.05.2022 17:23:54",
            "type", "USUAL",
            "person", new HashMap<>(Map.of(
                "height", 168,
                "passportId", "HGfgH8Skpsn65Slsm3dzyiowk",
                "location", new HashMap<>(Map.of(
                    "x", 921,
                    "y", 123,
                    "z", 700,
                    "name", "Saint Petersburg"
                ))
            ))
        ));


        Map<String, Object> resource = new HashMap<>(Map.of(
            "nextId", 1,
            "list", new ArrayList<>(
                List.of(a, b)
            )
        ));

        JsonTickets tickets = new JsonTickets(resource);

        {
            TicketEntry ticketA = tickets.ticketWithId(126);
            assertEquals(TicketType.VIP, ticketA.type());
            assertEquals("GFgfgHg78hG121ksSj23kOSs", ticketA.person().passportId());
        }

        {
            assertEquals(2, tickets.all().size());
        }
    }

    @Test
    public void insertion() {
        Ticket ticket = new Ticket() {
            @Override public String name() { return "Cinema"; }
            @Override public Coordinates coordinates() {
                return new Coordinates() {
                    @Override public double x() { return 10; }
                    @Override public double y() { return 10;}
                };
            }
            @Override public Optional<Integer> price() { return Optional.empty(); }
            @Override public TicketType type() { return TicketType.CHEAP; }
            @Override public Person person() {
                return new Person() {
                    @Override public int height() { return 164; }
                    @Override public String passportId() { return "12343423"; }
                    @Override public Location location() {
                        return new Location() {
                            @Override public float x() { return 10; }
                            @Override public double y() { return 10; }
                            @Override public float z() { return 10; }
                            @Override public String name() { return "Name"; }
                        };
                    }
                };
            }
        };

        Map<String, Object> resource = new HashMap<>(Map.of(
            "nextId", 5,
            "list", new ArrayList<>()
        ));

        JsonTickets tickets = new JsonTickets(resource);
        TicketResource res = tickets.insertionOf(ticket).execute().result();
        assertEquals(5, res.id());
        assertEquals("Cinema", res.name());

        res.coordinates().setX(67);
        List<Map<String, Object>> list = (List<Map<String, Object>>) resource.get("list");
        Map<String, Object> coords = (Map<String, Object>) list.get(list.size() - 1).get("coordinates");
        assertEquals(67D, (Double) coords.get("x"), 0.001);
    }
}
