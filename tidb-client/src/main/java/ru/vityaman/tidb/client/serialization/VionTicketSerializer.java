package ru.vityaman.tidb.client.serialization;

import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketType;
import ru.vityaman.vion.MapBasedVionObject;
import ru.vityaman.vion.VionObject;
import ru.vityaman.vion.VionSerializer;
import ru.vityaman.vion.exception.DeserializationException;
import ru.vityaman.vion.exception.FieldExtractionException;

public final class VionTicketSerializer
implements VionSerializer<Ticket> {
    private static final String NAME = "name";
    private static final String COORDINATES = "coordinates";
    private static final String PRICE = "price";
    private static final String TYPE = "type";
    private static final String PERSON = "person";

    private final VionCoordinatesSerializer coordinates;
    private final VionPersonSerializer person;

    public VionTicketSerializer(VionCoordinatesSerializer coordinates,
                                VionPersonSerializer person) {
        this.coordinates = coordinates;
        this.person = person;
    }

    @Override
    public VionObject serialize(Ticket object) {
        VionObject result = new MapBasedVionObject();
        result.put(NAME, object.name());
        result.put(
            COORDINATES, 
            coordinates.serialize(
                object.coordinates()
            )
        );
        if (object.price().isPresent()) {
            result.put(PRICE, object.price().get());
        }
        result.put(TYPE, object.type().toString());
        result.put(
            PERSON, 
            person.serialize(
                object.person()
            )
        );
        return result;
    }

    @Override
    public Ticket deserialize(VionObject json)
    throws DeserializationException {
        int jsonSize = json.keys().size();
        if (!(jsonSize == 5 && json.contains(PRICE) || jsonSize == 4)) {
            throw new DeserializationException(json,
                "must have only fields: " + 
                "'name', 'coordinates', 'person', 'type', optional 'price'"
            );
        }
        try {
            Ticket.Builder result = Ticket.fresh()
                    .withName(json.string(NAME))
                    .withCoordinates(
                        coordinates.deserialize(
                            json.object(COORDINATES)
                        )
                    )
                    .withPerson(
                        person.deserialize(
                            json.object(PERSON)
                        )
                    )
                    .withType(json.constant(TYPE, TicketType.class));
            if (json.contains(PRICE)) {
                result.withPrice(json.number(PRICE).intValue());
            }
            return result.instance();
        } catch (IllegalArgumentException | FieldExtractionException e) {
            throw new DeserializationException(json, e);
        }
    }
}
