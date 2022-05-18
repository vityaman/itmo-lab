package ru.vityaman.tidb.collection;

import java.time.Clock;
import java.util.AbstractSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ru.vityaman.tidb.collection.base.TicketDictionary;
import ru.vityaman.tidb.collection.data.Entry;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.exception.CapacityExceededException;
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

/**
 * Implementation of TicketDictionary using 
 * java.util.Map to store data.
 */
public class MapTicketsDictionary implements TicketDictionary {
    private final Clock clock;
    private int nextId;
    private final Map<String, TicketEntry> entriesByKey;
    
    public MapTicketsDictionary(int nextId, 
                                Map<String, TicketEntry> entriesByKey,
                                Clock clock) {
        this.nextId = nextId;
        this.entriesByKey = new HashMap<>(entriesByKey);
        this.clock = clock;
    }

    public MapTicketsDictionary(int nextId, 
                                Map<String, TicketEntry> entriesByKey) {
        this(nextId, entriesByKey, Clock.systemUTC());
    }

    int nextId() {
        return nextId;
    }

    @Override
    public void removeByKey(String key) 
    throws NoSuchEntryException {
        TicketEntry deletedEntry = entriesByKey.remove(key);
        if (deletedEntry == null) {
            throw NoSuchEntryException.withKey(key);
        }
    }

    @Override
    final public TicketEntry entryWithKey(String key) 
    throws NoSuchEntryException {
        TicketEntry entry = entriesByKey.get(key);
        if (entry == null) {
            throw NoSuchEntryException.withKey(key);
        }
        return entry;
    }

    @Override
    final public void updateByKey(String key, Ticket ticket) 
    throws NoSuchEntryException {
        entriesByKey.put(
            key, 
            entryWithKey(key).withDataUpdated(ticket)
        );
    }

    @Override
    public TicketEntry insert(String key, Ticket ticket) 
    throws EntryAlreadyExistsException, CapacityExceededException {
        if (entriesByKey.containsKey(key)) {
            throw EntryAlreadyExistsException.withKey(key);
        }
        int id = nextId++;
        if (id <= 0) {
            nextId--;
            throw new CapacityExceededException(
                "Collection capacity exceeded"
            );
        }
        TicketEntry entryToBeInserted = new TicketEntry(
            id, 
            Date.from(clock.instant()), 
            ticket
        );
        entriesByKey.put(key, entryToBeInserted);
        return entryToBeInserted;
    }

    @Override
    final public Set<Entry> all() {
        return new AbstractSet<Entry>() {
            Set<Map.Entry<String, TicketEntry>> entries
                = entriesByKey.entrySet();
            @Override
            public Iterator<Entry> iterator() {
                return new Iterator<Entry>() {
                    Iterator<Map.Entry<String, TicketEntry>> origin
                        = entries.iterator();

                    @Override
                    public boolean hasNext() {
                        return origin.hasNext();
                    }

                    @Override
                    public Entry next() {
                        Map.Entry<String, TicketEntry> entry = origin.next();
                        return new Entry.Simple(
                            entry.getKey(),
                            entry.getValue()
                        );
                    }
                };
            }

            @Override
            public int size() {
                return entries.size();
            }
        };
    }
}
