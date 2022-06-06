package ru.vityaman.tidb.client.serialization;

import ru.vityaman.tidb.api.data.TicketDataset;
import ru.vityaman.vion.MapBasedVionObject;
import ru.vityaman.vion.VionObject;
import ru.vityaman.vion.VionSerializer;
import ru.vityaman.vion.exception.DeserializationException;
import ru.vityaman.vion.exception.FieldExtractionException;

public final class VionDatasetSerializer
implements VionSerializer<TicketDataset> {
    private static final String NEXT_ID = "nextId";
    private static final String ALL = "all";

    private final VionTicketEntrySerializer vionTicketEntrySerializer;

    public VionDatasetSerializer(VionTicketEntrySerializer vionTicketEntrySerializer) {
        this.vionTicketEntrySerializer = vionTicketEntrySerializer;
    }

    @Override
    public VionObject serialize(TicketDataset object) {
        VionObject result = new MapBasedVionObject();
        result.put(NEXT_ID, object.nextId());
        VionObject all = new MapBasedVionObject();
        object.ticketsByKey().forEach((key, value) -> all.put(
                key, vionTicketEntrySerializer.serialize(value)
        ));
        result.put("all", all);
        return result;
    }

    // TODO: maybe rename to root
    @Override
    public TicketDataset deserialize(VionObject json)
    throws DeserializationException {
        if (json.keys().size() != 2) {
            throw new DeserializationException(json,
                "must have only fields: 'nextId', 'all'"
            );
        }
        try {
            TicketDataset.Builder dataset 
                    = TicketDataset.fresh().withNextId(
                        json.number(NEXT_ID).intValue()
                    );
            VionObject all = json.object(ALL);
            for (String key : all.keys()) {
                dataset.withEntry(key,
                    vionTicketEntrySerializer.deserialize(
                        all.object(key)
                    )
                );
            }
            return dataset.instance();
        } catch (FieldExtractionException | IllegalArgumentException e) {
            throw new DeserializationException(json, e);
        }
    }
}
