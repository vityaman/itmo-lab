package ru.vityaman.tidb.api.collection;

import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketEntry;
import ru.vityaman.tidb.api.exception.NoSuchEntryException;

/**
 * Interface for container, that performs
 * operations using id.
 */
public interface TicketDatabase {
    void removeById(int id) throws NoSuchEntryException;
    TicketEntry entryWithId(int id) throws NoSuchEntryException;
    void updateById(int id, Ticket ticket) throws NoSuchEntryException;
}
