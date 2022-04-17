package ru.vityaman.tidb.data.json;

import ru.vityaman.tidb.data.field.Convertable;
import ru.vityaman.tidb.data.field.Json;
import ru.vityaman.tidb.data.field.ReadonlyField;
import ru.vityaman.tidb.data.field.Verified;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.resource.TicketResource;
import ru.vityaman.tidb.data.resource.exception.InvalidResourceException;

import java.util.Date;
import java.util.Map;

/**
 * Json Ticket.
 */
public final class JsonTicketEntry extends JsonTicket
                                   implements TicketEntry, TicketResource {

    private final ReadonlyField<Integer> id;
    private final ReadonlyField<Date> creationDate;

    public JsonTicketEntry(Map<String, Object> json) {
        super(json);

        try {
            id = new Verified<>(
                    new Json<>("id", this.json),
                    TicketEntry.RequireValid::id);
            creationDate = new Convertable<>(
                    new Json<>("creationDate", this.json),
                    (date) -> new StringDate(date).asString(),
                    (string) -> new StringDate(string).asDate());
        } catch (InvalidValueException | Json.InvalidJsonException e) {
            throw new InvalidResourceException(
                    "Invalid resource " + json + " as " + e.getMessage(), e);
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
