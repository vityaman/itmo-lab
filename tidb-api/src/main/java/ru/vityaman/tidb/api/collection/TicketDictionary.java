package ru.vityaman.tidb.api.collection;

import java.util.Set;

import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketEntry;
import ru.vityaman.tidb.api.exception.CapacityExceededException;
import ru.vityaman.tidb.api.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.api.exception.NoSuchEntryException;


/**
 * Interface of collection, that performs
 * operations using key.
 */
public interface TicketDictionary {
    void removeByKey(String key) throws NoSuchEntryException;

    TicketEntry entryWithKey(String key) throws NoSuchEntryException;

    void updateByKey(String key, Ticket ticket) throws NoSuchEntryException;

    TicketEntry insert(String key, Ticket ticket) 
    throws EntryAlreadyExistsException, CapacityExceededException;
    
    Set<Entry> all();
}
