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
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

public interface TicketCollection {
    public Collection<Entry> all();
    public TicketEntry insert(String key, Ticket ticket) throws EntryAlreadyExistsException;
    public void updateById(int id, Ticket ticket) throws NoSuchEntryException;
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
