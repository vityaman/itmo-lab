package ru.vityaman.tidb.client.serialization;

import java.text.DateFormat;
import java.text.ParseException;

import ru.vityaman.tidb.api.data.TicketEntry;
import ru.vityaman.vion.VionObject;
import ru.vityaman.vion.VionSerializer;
import ru.vityaman.vion.exception.DeserializationException;
import ru.vityaman.vion.exception.FieldExtractionException;

public final class VionTicketEntrySerializer
implements VionSerializer<TicketEntry> {
    private static final String ID = "id";
    private static final String CREATION_DATE = "creationDate";

    private final VionTicketSerializer ticket;
    private final DateFormat creationDateFormat;

    public VionTicketEntrySerializer(VionTicketSerializer ticket,
                                     DateFormat creationDateFormat) {
        this.ticket = ticket;
        this.creationDateFormat = creationDateFormat;
    }

    @Override
    public VionObject serialize(TicketEntry object) {
        VionObject result = ticket.serialize(object);
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
    public TicketEntry deserialize(VionObject json)
    throws DeserializationException {
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
                ticket.deserialize(json)
            );
            json.put(ID, id);
            json.put(CREATION_DATE, creationDate);
            return result;
        } catch (FieldExtractionException
                 | IllegalArgumentException
                 | ParseException e) {
            throw new DeserializationException(json, e);
        }
    }
}
