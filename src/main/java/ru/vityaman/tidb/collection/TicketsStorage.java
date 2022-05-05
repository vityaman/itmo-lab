package ru.vityaman.tidb.collection;

import java.time.Clock;
import java.util.Map;
import java.util.stream.Collectors;

import ru.vityaman.tidb.collection.base.TicketDatabase;
import ru.vityaman.tidb.collection.data.Entry;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

public final class TicketsStorage extends MapTicketsDictionary 
                                        implements TicketDatabase {
    private final Map<Integer, String> keysById;

    public TicketsStorage(int nextId, 
                          Map<String, TicketEntry> entriesByKey) {
        this(nextId, entriesByKey, Clock.systemUTC());
    }

    public TicketsStorage(int nextId, 
                          Map<String, TicketEntry> entriesByKey, 
                          Clock clock) {
        super(nextId, entriesByKey, clock);
        this.keysById = all().stream()
            .collect(Collectors.<Entry, Integer, String>
                toMap(
                    (entry) -> entry.ticket().id(),
                    (entry) -> entry.key()
                )
            );
    }
    
    @Override
    public void removeByKey(String key) throws NoSuchEntryException {
        keysById.remove(entryWithKey(key).id());
        super.removeByKey(key);
    }

    @Override
    public TicketEntry insert(String key, Ticket ticket) 
    throws EntryAlreadyExistsException {
        TicketEntry inserted = super.insert(key, ticket);
        keysById.put(inserted.id(), key);
        return inserted;
    }

    @Override
    public void removeById(int id) throws NoSuchEntryException {
        removeByKey(keyAssociatedWithId(id));
    }

    @Override
    public TicketEntry entryWithId(int id) throws NoSuchEntryException {
        return entryWithKey(keyAssociatedWithId(id));
    }

    @Override
    public void updateById(int id, Ticket ticket) throws NoSuchEntryException {
        updateByKey(keyAssociatedWithId(id), ticket);
    }

    private String keyAssociatedWithId(int id) throws NoSuchEntryException {
        String key = keysById.get(id);
        if (key == null) {
            throw NoSuchEntryException.withId(id);
        }
        return key;
    }    
}
