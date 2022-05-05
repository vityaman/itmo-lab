package ru.vityaman.tidb.collection.base;

import java.util.Set;

import ru.vityaman.tidb.collection.data.Entry;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

public interface TicketDictionary {
    void removeByKey(String key) throws NoSuchEntryException;
    TicketEntry entryWithKey(String key) throws NoSuchEntryException;
    void updateByKey(String key, Ticket ticket) throws NoSuchEntryException;
    TicketEntry insert(String key, Ticket ticket) throws EntryAlreadyExistsException;
    Set<Entry> all();
}
