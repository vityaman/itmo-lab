package ru.vityaman.tidb.data.json;

import ru.vityaman.tidb.data.field.*;
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

    public JsonTicket(Map<String, Object> json) {
        super(json);

        try {
            this.name = new Verified<>(new Json<>("name", this.json),
                Ticket.RequireValid::name);
            this.price = new OptionalVerified<>(
                new OptionalJson<>("price", this.json),
                Ticket.RequireValid::price);
            this.type = new Convertable<TicketType, String>(
                new Json<>("type", this.json),
                Enum::toString,
                TicketType::valueOf);
            this.coordinates = new JsonCoordinates(new Json<Map<String, Object>>(
                "coordinates", this.json).value());
            this.person = new JsonPerson(new Json<Map<String, Object>>(
                "person", this.json).value());
        } catch (InvalidValueException
                | Json.InvalidJsonException
                | InvalidResourceException e) {
            throw new InvalidResourceException(
                    "Invalid resource " + json + " as " + e.getMessage(), e);
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
