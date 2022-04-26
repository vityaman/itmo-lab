package ru.vityaman.tidb.data.json;

import ru.vityaman.tidb.data.json.exception.InvalidJsonResourceException;
import ru.vityaman.tidb.data.json.field.Convertable;
import ru.vityaman.tidb.data.json.field.Field;
import ru.vityaman.tidb.data.json.field.JsonField;
import ru.vityaman.tidb.data.json.field.OptionalField;
import ru.vityaman.tidb.data.json.field.OptionalJson;
import ru.vityaman.tidb.data.json.field.OptionalVerified;
import ru.vityaman.tidb.data.json.field.Verified;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketType;
import ru.vityaman.tidb.data.resource.exception.InvalidResourceException;

import java.util.Map;
import java.util.Optional;

/**
 * Json Ticket resource.
 */
public class JsonTicket extends JsonResource
                        implements Ticket {

    private final Field<String> name;
    private final OptionalField<Integer> price;
    private final Field<TicketType> type;
    private final JsonCoordinates coordinates;
    private final JsonPerson person;

    public JsonTicket(Map<String, Object> json)
                                    throws InvalidJsonResourceException {
        super(json);
        try {
            this.name = new Verified<>(
                new JsonField<>(this.json, "name"),
                Ticket.RequireValid::name
            );
            this.price = new OptionalVerified<>(
                new OptionalJson<>(this.json, "price"),
                Ticket.RequireValid::price
            );
            this.type = new Convertable<TicketType, String>(
                new JsonField<>(this.json, "type"),
                Enum::toString,
                TicketType::valueOf
            );
            this.coordinates = new JsonCoordinates(
                new JsonField<Map<String, Object>>(this.json,
                    "coordinates").value()
            );
            this.person = new JsonPerson(
                new JsonField<Map<String, Object>>(this.json,
                    "person").value()
            );
        } catch (InvalidValueException
                | JsonField.InvalidJsonException
                | InvalidResourceException e) {
            throw new InvalidJsonResourceException(json, e);
        }
    }

    @Override
    public String name() {
        return name.value();
    }

    @Override
    public JsonCoordinates coordinates() {
        return coordinates;
    }

    @Override
    public Optional<Integer> price() {
        if (!price.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(price.value());
    }

    @Override
    public TicketType type() {
        return type.value();
    }

    @Override
    public JsonPerson person() {
        return person;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public void removePrice() {
        price.remove();
    }

    public void setType(TicketType type) {
        this.type.set(type);
    }
}
