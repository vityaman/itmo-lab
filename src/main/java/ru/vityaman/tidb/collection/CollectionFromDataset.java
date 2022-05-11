package ru.vityaman.tidb.collection;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.data.Entry;
import ru.vityaman.tidb.collection.data.Person;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketDataset;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.data.TicketType;
import ru.vityaman.tidb.collection.data.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.exception.EntryAlreadyExistsException;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

public final class CollectionFromDataset implements TicketCollection {
    private final TicketsStorage storage;
    
    public CollectionFromDataset(TicketDataset tickets) {
        storage = new TicketsStorage(
            tickets.nextId(), 
            tickets.ticketsByKey()
        );
    }

    public TicketDataset dataset() {
        TicketDataset.Builder dataset = TicketDataset.fresh();
        try {
            dataset.withNextId(storage.nextId());
            for (Entry entry : all()) {
                dataset.withEntry(entry.key(), entry.ticket());
            }
        } catch (InvalidFieldValueException e) {
            // It can happen as storage is a trusted service
            throw new AssertionError(e);
        }
        return dataset.instance();
    }

    @Override
    public Collection<Entry> all() {
        return storage.all();
    }

    @Override
    public TicketEntry insert(String key, Ticket ticket) throws EntryAlreadyExistsException {
        return storage.insert(key, ticket);
    }

    @Override
    public void updateById(int id, Ticket ticket) throws NoSuchEntryException {
        storage.updateById(id, ticket);
    }

    @Override
    public void removeEntryWithKey(String key) throws NoSuchEntryException {
        storage.removeByKey(key);    
    }

    // TODO: code repetition : aaaaaaaaaaaaaaaAAAAAAAaaaaaaaaaaAAAAAAAa
    public void clear() {
        removeEntriesWithKeys( 
            storage.all().stream()
                   .map(Entry::key)
                   .collect(Collectors.toSet())
        );
    }

    public void removeAllThoseLessThan(TicketEntry given, 
                                       Comparator<TicketEntry> comparison) {
        removeEntriesWithIds(
            storage.all().stream()
                    .map(Entry::ticket)
                    .filter(e -> 
                        comparison.compare(e, given) < 0
                    )
                    .mapToInt(TicketEntry::id)
                    .toArray()
        );
    }

    public void removeAllThoseWithKeyLessThan(String given, 
                                              Comparator<String> comparison) {
        removeEntriesWithKeys(
            storage.all().stream()
                   .map(Entry::key)
                   .filter(k -> comparison
                            .compare(k, given) < 0)
                   .collect(Collectors.toSet())
        );
    }

    public Map<LocalDate, List<TicketEntry>> entriesGroupedByCreationDate() {
        // notice that this creation date is not that creation date 
        // in ticket - this is without time
        Function<TicketEntry, LocalDate> creationDate = entry -> {
            Date date = entry.creationDate();
            return LocalDate.of(
                date.getYear(), 
                date.getMonth(), 
                date.getDay()
            );
        };

        return storage.all().stream()
                .map(Entry::ticket)
                .collect(Collectors.groupingBy(creationDate));
    }

    public int countOfEntriesWithPersonGreaterThan(Person given, 
                                                   Comparator<Person> comparison) {
        // cast to int is ok as ticket.id is int
        return (int) storage.all().stream()
                        .map(Entry::ticket)
                        .filter(entry -> 
                            comparison.compare(
                                entry.person(), 
                                given
                            ) > 0
                        )
                        .count();
    }

    public Collection<TicketEntry> entriesWithTypeGreaterThan(TicketType given) {
        return storage.all().stream()
                    .map(Entry::ticket)
                    .filter(entry -> entry.type().compareTo(given) > 0)
                    .collect(Collectors.toList());
    }

    private void removeEntriesWithKeys(Set<String> keys) {
        try {
            for (String key : keys) {
                storage.removeByKey(key);       
            }
        } catch (NoSuchEntryException ignored) {
            // It's ok as it could happen only if
            // someone in other thread was deleted one of entries
            // while this deleting process
        }
    }

    private void removeEntriesWithIds(int[] ids) {
        try {
            for (int id : ids) {
                storage.removeById(id);       
            }
        } catch (NoSuchEntryException ignored) {
            // It's ok as it could happen only if
            // someone in other thread was deleted one of entries
            // while this deleting process
        }
    }
}
