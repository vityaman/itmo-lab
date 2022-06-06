package ru.vityaman.tidb.api.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import ru.vityaman.tidb.api.collection.Entry;
import ru.vityaman.tidb.api.data.SimpleEntry;

public final class EntrySetSerializer implements ObjectSerializer<Set<Entry>> {
    private final TicketEntrySerializer ticketEntrySerializer;

    public EntrySetSerializer(TicketEntrySerializer ticketEntrySerializer) {
        this.ticketEntrySerializer = ticketEntrySerializer;
    }

    @Override
    public Set<Entry> read(ObjectInputStream input)
            throws IOException, DeserializationException
    {
        return ObjectSerializer.deserialized(() -> {
            Set<Entry> entries = new HashSet<>();
            int count = input.readInt();
            for (int i = 0; i < count; i++) {
                entries.add(new SimpleEntry(
                        (String) input.readObject(),
                        ticketEntrySerializer.read(input)
                    )
                );
            }
            return entries;
        });
    }

    @Override
    public void write(ObjectOutputStream output, Set<Entry> object)
    throws IOException {
        output.writeInt(object.size());
        for (Entry entry : object) {
            output.writeObject(entry.key());
            ticketEntrySerializer.write(output, entry.ticket());
        }
    }

    public TicketEntrySerializer ticketEntrySerializator() {
        return ticketEntrySerializer;
    }
}
