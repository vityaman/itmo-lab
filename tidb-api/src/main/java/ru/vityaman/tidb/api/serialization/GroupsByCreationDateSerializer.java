package ru.vityaman.tidb.api.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.vityaman.tidb.api.data.TicketEntry;

public final class GroupsByCreationDateSerializer
implements ObjectSerializer<Map<LocalDate, List<TicketEntry>>> {
    private final TicketEntrySerializer ticketEntrySerializer;

    public GroupsByCreationDateSerializer(
        TicketEntrySerializer ticketEntrySerializer
    ) {
        this.ticketEntrySerializer = ticketEntrySerializer;
    }

    @Override
    public Map<LocalDate, List<TicketEntry>> read(ObjectInputStream stream)
    throws IOException, DeserializationException {
        return ObjectSerializer.deserialized(() -> {
            int groupsCount = stream.readInt();
            if (groupsCount < 0) {
                throw new DeserializationException(
                    "groupsCount must be in [0, MAX_INT]"
                );
            }
            Map<LocalDate, List<TicketEntry>> result = new HashMap<>();
            for (int i = 0; i < groupsCount; i++) {
                LocalDate localDate = (LocalDate) stream.readObject();
                int entriesCount = stream.readInt();
                if (entriesCount <= 0) {
                    throw new DeserializationException(
                        "entriesCount must be in [1, MAX_INT]"
                    );
                }
                List<TicketEntry> list = new ArrayList<>();
                for (int j = 0; j < entriesCount; j++) {
                    list.add(ticketEntrySerializer.read(stream));
                }
                result.put(localDate, list);
            }
            return result;
        });
    }

    @Override
    public void write(
        ObjectOutputStream stream, Map<LocalDate, List<TicketEntry>> object
    ) throws IOException {
        stream.writeInt(object.size());
        for (Map.Entry<LocalDate, List<TicketEntry>> entry : object.entrySet()) {
            stream.writeObject(entry.getKey());
            List<TicketEntry> list = entry.getValue();
            stream.writeInt(list.size());
            for (TicketEntry ticket : list) {
                ticketEntrySerializer.write(stream, ticket);
            }
        }
    }
}
