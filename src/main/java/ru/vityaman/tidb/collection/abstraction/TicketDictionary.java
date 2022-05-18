package ru.vityaman.tidb.collection.abstraction;

import java.util.Set;

import ru.vityaman.tidb.collection.data.valid.entity.Ticket;
import ru.vityaman.tidb.collection.data.valid.entity.TicketEntry;
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

public interface TicketDictionary {
    void removeByKey(String key) throws NoSuchEntryException;
    TicketEntry entryWithKey(String key) throws NoSuchEntryException;
    void updateByKey(String key, Ticket ticket) throws NoSuchEntryException;
    TicketEntry insert(String key, Ticket ticket) throws EntryAlreadyExistsException;
    Set<Entry> all();
}
