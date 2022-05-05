package ru.vityaman.tidb.collection.json.serialize;

import ru.vityaman.tidb.collection.data.TicketDataset;
import ru.vityaman.tidb.collection.data.exception.InvalidFieldValueException;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.JsonSerialization;
import ru.vityaman.tidb.lang.json.MapJsonObject;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;
import ru.vityaman.tidb.lang.json.exception.JsonExtractionException;

public final class DatasetSerialization 
implements JsonSerialization<TicketDataset> {
    private static final String NEXT_ID = "nextId";
    private static final String ALL = "all";

    private final TicketEntrySerialization ticketEntrySerialization;

    public DatasetSerialization(TicketEntrySerialization ticketEntrySerialization) {
        this.ticketEntrySerialization = ticketEntrySerialization;
    }

    @Override
    public JsonObject serialized(TicketDataset object) {
        JsonObject result = new MapJsonObject();
        result.put(NEXT_ID, object.nextId());
        JsonObject all = new MapJsonObject();
        object.ticketsByKey().entrySet()
        .forEach(entry -> all.put(
                entry.getKey(), 
                ticketEntrySerialization.serialized(
                    entry.getValue()
                )
            )
        );
        result.put("all", all);
        return result;
    }

    // TODO: maybe rename to root
    @Override
    public TicketDataset deserialized(JsonObject json) 
    throws JsonDeserializationException {
        if (json.keys().size() != 2) {
            throw new JsonDeserializationException(json, 
                "must have only fields: 'nextId', 'all'"
            );
        }
        try {
            TicketDataset.Builder dataset 
                    = TicketDataset.fresh().withNextId(
                        json.number(NEXT_ID).intValue()
                    );
            JsonObject all = json.object(ALL);
            for (String key : all.keys()) {
                dataset.withEntry(
                    key, 
                    ticketEntrySerialization.deserialized(
                        all.object(key)
                    )
                );
            }
            return dataset.instance();
        } catch (JsonExtractionException | InvalidFieldValueException e) {
            throw new JsonDeserializationException(json, e);
        }
    }
}
