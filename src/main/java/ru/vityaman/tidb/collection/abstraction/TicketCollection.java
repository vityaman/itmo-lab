package ru.vityaman.tidb.collection.abstraction;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ru.vityaman.tidb.collection.data.valid.entity.Person;
import ru.vityaman.tidb.collection.data.valid.entity.Ticket;
import ru.vityaman.tidb.collection.data.valid.entity.TicketEntry;
import ru.vityaman.tidb.collection.data.valid.entity.TicketType;
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

public interface TicketCollection {
    public Collection<Entry> all();

    public TicketEntry insert(String key, Ticket ticket) 
    throws EntryAlreadyExistsException;

    public void updateById(int id, Ticket ticket) throws NoSuchEntryException;

    public void clear();

    public void removeEntryWithKey(String key) throws NoSuchEntryException;

    public void removeAllThoseLessThan(
        Ticket given, 
        Comparator<Ticket> comparison
    );

    public void removeAllThoseWithKeyLessThan(
        String given, 
        Comparator<String> comparison
    );

    public Map<LocalDate, List<TicketEntry>> entriesGroupedByCreationDate();

    public int countOfEntriesWithPersonGreaterThan(
        Person given, 
        Comparator<Person> comparison
    );

    public Collection<TicketEntry> entriesWithTypeGreaterThan(TicketType given);
}
