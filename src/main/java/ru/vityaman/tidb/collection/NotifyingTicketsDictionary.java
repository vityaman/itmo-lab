package ru.vityaman.tidb.collection;

import java.util.HashSet;
import java.util.Set;

import ru.vityaman.tidb.collection.base.TicketDictionary;
import ru.vityaman.tidb.collection.data.Entry;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.exception.CapacityExceededException;
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

public final class NotifyingTicketsDictionary implements TicketDictionary {
    private final TicketDictionary origin;

    private final Event.TicketInsertion ticketInsertion;
    private final Event.KeyRemoval keyRemoval;

    public NotifyingTicketsDictionary(TicketDictionary origin) {
        this.origin = origin;
        this.ticketInsertion = new Event.TicketInsertion();
        this.keyRemoval = new Event.KeyRemoval();
    }

    @Override
    public void removeByKey(String key) 
    throws NoSuchEntryException {
        keyRemoval.occure(key);
        origin.removeByKey(key);
    }

    @Override
    public TicketEntry entryWithKey(String key) 
    throws NoSuchEntryException {
        return origin.entryWithKey(key);
    }

    @Override
    public void updateByKey(String key, Ticket ticket) 
    throws NoSuchEntryException {
        origin.updateByKey(key, ticket);
    }

    @Override
    public TicketEntry insert(String key, Ticket ticket) 
    throws EntryAlreadyExistsException, CapacityExceededException {
        TicketEntry inserted = origin.insert(key, ticket);
        ticketInsertion.occure(inserted);
        return inserted;
    }

    @Override
    public Set<Entry> all() {
        return origin.all();
    }

    // TODO: duplicate code
    public static final class Event {
        public static final class TicketInsertion {
            private final Set<Observer> observers = new HashSet<>();

            public void subscribe(Observer observer) {
                observers.add(observer);
            }

            private void occure(TicketEntry inserted) {
                observers.forEach(o -> o.onTicketInsertion(inserted));
            }

            public static interface Observer {
                void onTicketInsertion(TicketEntry inserted);
            }   
        }

        public static final class KeyRemoval {
            private final Set<Observer> observers = new HashSet<>();

            public void subscribe(Observer observer) {
                observers.add(observer);
            }

            private void occure(String key) {
                observers.forEach(o -> o.beforeKeyRemoved(key));
            }
            
            public static interface Observer {
                void beforeKeyRemoved(String key);
            }
        }
    }
}
