package ru.vityaman.tidb.collection.base;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ru.vityaman.tidb.collection.data.Entry;
import ru.vityaman.tidb.collection.data.Person;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.data.TicketType;
import ru.vityaman.tidb.collection.exception.CapacityExceededException;
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

/**
 * Interface of collection.
 */
public interface TicketCollection {
    /**
     * @return all collection entries
     */
    public Collection<Entry> all();

    /**
     * @param key mapped key
     * @param ticket to insert
     * @return ticket with generated id and creationDate
     * @throws EntryAlreadyExistsException
     * @throws CapacityExceededException
     */
    public TicketEntry insert(String key, Ticket ticket)
    throws EntryAlreadyExistsException, CapacityExceededException;

    /**
     * @param id
     * @param ticket
     * @throws NoSuchEntryException
     */
    public void updateById(int id, Ticket ticket) throws NoSuchEntryException;

    /**
     * Deletes all entries in collection.
     */
    public void clear();
    public void removeEntryWithKey(String key) throws NoSuchEntryException;
    public void removeAllThoseLessThan(TicketEntry given, 
                                       Comparator<TicketEntry> comparison);
    public void removeAllThoseWithKeyLessThan(String given, 
                                              Comparator<String> comparison);
    public Map<LocalDate, List<TicketEntry>> entriesGroupedByCreationDate();
    public int countOfEntriesWithPersonGreaterThan(Person given, 
                                                   Comparator<Person> comparison);
    public Collection<TicketEntry> entriesWithTypeGreaterThan(TicketType given);
}
