package ru.vityaman.tidb.api.collection;

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

/**
 * Interface of collection.
 */
public interface TicketCollection extends RemoteTicketCollection {
    /**
     * @return all collection entries
     */
    @Override
    public Collection<Entry> all();

    /**
     * @param key mapped key
     * @param ticket to insert
     * @return ticket with generated id and creationDate
     * @throws EntryAlreadyExistsException
     * @throws CapacityExceededException
     */
    @Override
    public TicketEntry insert(String key, Ticket ticket)
    throws EntryAlreadyExistsException, CapacityExceededException;

    /**
     * @param id
     * @param ticket
     * @throws NoSuchEntryException
     */
    @Override
    public void updateById(int id, Ticket ticket) throws NoSuchEntryException;

    /**
     * Deletes all entries in collection.
     */
    @Override
    public void clear();

    @Override
    public void removeEntryWithKey(String key) throws NoSuchEntryException;

    @Override
    public void removeAllThoseLessThan(TicketEntry given);

    public void removeAllThoseWithKeyLessThan(String given);

    public Map<LocalDate, List<TicketEntry>> entriesGroupedByCreationDate();

    public int countOfEntriesWithPersonGreaterThan(Person given);
    
    public Collection<TicketEntry> entriesWithTypeGreaterThan(TicketType given);
}
