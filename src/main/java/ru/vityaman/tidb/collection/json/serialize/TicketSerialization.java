package ru.vityaman.tidb.collection.json.serialize;

import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketType;
import ru.vityaman.tidb.collection.data.exception.InvalidFieldValueException;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.JsonSerialization;
import ru.vityaman.tidb.lang.json.MapJsonObject;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;
import ru.vityaman.tidb.lang.json.exception.JsonExtractionException;

public final class TicketSerialization 
implements JsonSerialization<Ticket> {
    private static final String NAME = "name";
    private static final String COORDINATES = "coordinates";
    private static final String PRICE = "price";
    private static final String TYPE = "type";
    private static final String PERSON = "person";

    private final CoordinatesSerialization coordinates;
    private final PersonSerialization person;

    public TicketSerialization(CoordinatesSerialization coordinates, 
                               PersonSerialization person) {
        this.coordinates = coordinates;
        this.person = person;
    }

    @Override
    public JsonObject serialized(Ticket object) {
        JsonObject result = new MapJsonObject();
        result.put(NAME, object.name());
        result.put(
            COORDINATES, 
            coordinates.serialized(
                object.coordinates()
            )
        );
        if (object.price().isPresent()) {
            result.put(PRICE, object.price().get());
        }
        result.put(TYPE, object.type().toString());
        result.put(
            PERSON, 
            person.serialized(
                object.person()
            )
        );
        return result;
    }

    @Override
    public Ticket deserialized(JsonObject json) 
    throws JsonDeserializationException {
        int jsonSize = json.keys().size();
        if (!(jsonSize == 5 && json.contains(PRICE) || jsonSize == 4)) {
            throw new JsonDeserializationException(json, 
                "must have only fields: " + 
                "'name', 'coordinates', 'person', 'type', optional 'price'"
            );
        }
        try {
            Ticket.Builder result = Ticket.fresh()
                    .withName(json.string(NAME))
                    .withCoordinates(
                        coordinates.deserialized(
                            json.object(COORDINATES)
                        )
                    )
                    .withPerson(
                        person.deserialized(
                            json.object(PERSON)
                        )
                    )
                    .withType(json.constant(TYPE, TicketType.class));
            if (json.contains(PRICE)) {
                result.withPrice(json.number(PRICE).intValue());
            }
            return result.instance();
        } catch (InvalidFieldValueException | JsonExtractionException e) {
            throw new JsonDeserializationException(json, e);
        }
    }
}
