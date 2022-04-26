package ru.vityaman.tidb.data.json;

import ru.vityaman.tidb.data.json.exception.InvalidJsonResourceException;
import ru.vityaman.tidb.data.json.field.Convertable;
import ru.vityaman.tidb.data.json.field.JsonField;
import ru.vityaman.tidb.data.json.field.ReadonlyField;
import ru.vityaman.tidb.data.json.field.Verified;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.TicketResource;

import java.util.Date;
import java.util.Map;

/**
 * Json Ticket.
 */
public final class JsonTicketEntry extends JsonTicket
                                   implements TicketResource {

    private final ReadonlyField<Integer> id;
    private final ReadonlyField<Date> creationDate;

    public JsonTicketEntry(Map<String, Object> json)
                                            throws InvalidJsonResourceException {
        super(json);
        try {
            id = new Verified<>(
                new JsonField<>(this.json, "id"),
                TicketEntry.RequireValid::id);
            creationDate = new Convertable<>(
                new JsonField<>(this.json, "creationDate"),
                (date) -> new StringDate(date).asString(),
                (string) -> new StringDate(string).asDate());
        } catch (InvalidValueException
                | JsonField.InvalidJsonException e) {
            throw new InvalidJsonResourceException(json, e);
        }
    }

    @Override
    public int id() {
        return id.value();
    }

    @Override
    public Date creationDate() {
        return creationDate.value();
    }
}
