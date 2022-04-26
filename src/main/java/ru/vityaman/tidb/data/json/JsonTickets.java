package ru.vityaman.tidb.data.json;

import java.util.*;
import java.time.Clock;

import ru.vityaman.tidb.data.json.exception.InvalidJsonResourceException;
import ru.vityaman.tidb.data.json.field.Field;
import ru.vityaman.tidb.data.json.field.JsonField;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.exception.InvalidResourceException;
import ru.vityaman.tidb.data.resource.exception.ResourceNotFoundException;
import ru.vityaman.tidb.data.resource.TicketResource;
import ru.vityaman.tidb.data.resource.Tickets;

/**
 * Json Tickets.
 */
public final class JsonTickets extends JsonResource
                               implements Tickets {

    private final Clock clock;
    private final Field<Integer> nextId;
    private final List<Map<String, Object>> list;
    private final Map<Integer, Map<String, Object>> ticketById;

    public JsonTickets(Map<String, Object> json)
                                            throws InvalidJsonResourceException,
                                                   InvalidResourceException {
        this(json, Clock.systemUTC());
    }

    public JsonTickets(Map<String, Object> json, Clock clock)
                                                throws InvalidJsonResourceException,
                                                       InvalidResourceException {
        super(json);
        this.clock = clock;
        try {
            this.nextId = new JsonField<>(this.json, "nextId");
            this.list = new JsonField<List<Map<String, Object>>>(
                this.json, "list"
            ).value();

            this.ticketById = new HashMap<>(list.size());
            for (Map<String, Object> ticket : list) {
                TicketEntry entry = new JsonTicketEntry(ticket);
                if (ticketById.containsKey(entry.id())) {
                    throw new InvalidResourceException(String.format(
                        "Duplicate key %s", entry
                    ));
                }
                ticketById.put(entry.id(), ticket);
            }
        } catch (InvalidValueException | JsonField.InvalidJsonException e) {
            throw new InvalidJsonResourceException(json, e);
        }
    }

    @Override
    public JsonTicketEntry ticketWithId(int id) throws ResourceNotFoundException {
        Map<String, Object> ticket = ticketById.get(id);
        if (ticket == null) {
            throw new ResourceNotFoundException(String.format(
                "No ticket with id %d", id)
            );
        }
        return new JsonTicketEntry(ticket);
    }

    @Override
    public Collection<TicketResource> all() {
        return new AbstractCollection<TicketResource>() {
            private final Collection<Map<String, Object>> tickets =
                    ticketById.values();

            @Override
            public Iterator<TicketResource> iterator() {
                return new Iterator<TicketResource>() {
                    private final Iterator<Map<String, Object>> iterator =
                            tickets.iterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public JsonTicketEntry next() {
                        return new JsonTicketEntry(iterator.next());
                    }

                };
            }

            @Override
            public int size() {
                return tickets.size();
            }
        };
    }

    @Override
    public Insertion insertionOf(Ticket ticket) {
        return new JsonInsertion(ticket);
    }

    @Override
    public void removeWithById(int id) throws ResourceNotFoundException {
        Object removed = ticketById.remove(id);
        if (removed == null) {
            throw new ResourceNotFoundException(String.format(
                "No ticket with id %s", id)
            );
        }
        list.remove(removed);
    }

    private final class JsonInsertion implements Insertion {
        private final Ticket ticket;

        private JsonTicketEntry resource;

        public JsonInsertion(Ticket ticket) {
            this.ticket = ticket;
        }

        @Override
        public JsonInsertion execute() {
            Map<String, Object> json = ticket.json();
            json.put("id", JsonTickets.this.nextId());
            json.put("creationDate",
                    new StringDate(Date.from(clock.instant()))
                            .asString());

            JsonTickets.this.list.add(json);
            resource = new JsonTicketEntry(json);
            JsonTickets.this.ticketById.put(resource.id(), json);

            return this;
        }

        @Override
        public JsonTicketEntry result() {
            if (resource == null) {
                throw new IllegalStateException(
                    "You must execute insertion before asking for result"
                );
            }
            return resource;
        }
    }

    private int nextId() {
        int id = nextId.value();
        nextId.set(id + 1);
        return id;
    }

    public Map<String, Object> json() {
        return new HashMap<>(json);
    }

    public static Map<String, Object> defaultJson() {
        return new HashMap<String, Object>() {{
            put("nextId", 1);
            put("list", new ArrayList<>());
        }};
    }
}
