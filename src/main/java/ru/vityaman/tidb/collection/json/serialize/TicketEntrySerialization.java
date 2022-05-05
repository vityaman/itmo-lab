package ru.vityaman.tidb.collection.json.serialize;

import java.text.DateFormat;
import java.text.ParseException;

import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.JsonSerialization;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;
import ru.vityaman.tidb.lang.json.exception.JsonExtractionException;

public final class TicketEntrySerialization 
implements JsonSerialization<TicketEntry> {
    private static final String ID = "id";
    private static final String CREATION_DATE = "creationDate";

    private final TicketSerialization ticket;
    private final DateFormat creationDateFormat;

    public TicketEntrySerialization(TicketSerialization ticket, 
                                    DateFormat creationDateFormat) {
        this.ticket = ticket;
        this.creationDateFormat = creationDateFormat;
    }

    @Override
    public JsonObject serialized(TicketEntry object) {
        JsonObject result = ticket.serialized(object);
        result.put(ID, object.id());
        result.put(
            CREATION_DATE, 
            creationDateFormat.format(
                object.creationDate()
            )
        );
        return result;
    }

    @Override
    public TicketEntry deserialized(JsonObject json) 
    throws JsonDeserializationException {
        try {
            int id = json.number(ID).intValue();
            String creationDate = json.string(CREATION_DATE);
            json.remove(ID);
            json.remove(CREATION_DATE);
            TicketEntry result = new TicketEntry(
                id,
                creationDateFormat.parse(
                    creationDate
                ),
                ticket.deserialized(json)
            );
            json.put(ID, id);
            json.put(CREATION_DATE, creationDate);
            return result;
        } catch (JsonExtractionException | ParseException e) {
            throw new JsonDeserializationException(json, e);
        }
    }
}
