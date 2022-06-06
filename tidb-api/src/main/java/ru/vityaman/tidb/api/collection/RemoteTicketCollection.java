package ru.vityaman.tidb.api.collection;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import ru.vityaman.tidb.api.data.Person;
import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketEntry;
import ru.vityaman.tidb.api.data.TicketType;
import ru.vityaman.tidb.api.exception.CapacityExceededException;
import ru.vityaman.tidb.api.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.api.exception.NoSuchEntryException;

public interface RemoteTicketCollection {
    /**
     * @return all collection entries
     */
    public Collection<Entry> all() throws IOException;

    /**
     * @param key mapped key
     * @param ticket to insert
     * @return ticket with generated id and creationDate
     * @throws EntryAlreadyExistsException
     * @throws CapacityExceededException
     */
    public TicketEntry insert(String key, Ticket ticket)
    throws EntryAlreadyExistsException, CapacityExceededException, 
           IOException;

    /**
     * @param id
     * @param ticket
     * @throws NoSuchEntryException
     */
    public void updateById(int id, Ticket ticket) 
    throws NoSuchEntryException, IOException;

    /**
     * Deletes all entries in collection.
     */
    public void clear() throws IOException;

    public void removeEntryWithKey(String key) 
    throws NoSuchEntryException, IOException;

    public void removeAllThoseLessThan(TicketEntry given) throws IOException;

    public void removeAllThoseWithKeyLessThan(String given) throws IOException;

    public Map<LocalDate, List<TicketEntry>> entriesGroupedByCreationDate()
    throws IOException;

    public int countOfEntriesWithPersonGreaterThan(Person given) throws IOException;
    
    public Collection<TicketEntry> entriesWithTypeGreaterThan(TicketType given)
    throws IOException;
}
