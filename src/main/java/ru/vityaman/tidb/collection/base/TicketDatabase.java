package ru.vityaman.tidb.collection.base;

import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketEntry;
import ru.vityaman.tidb.collection.exception.NoSuchEntryException;

public interface TicketDatabase {
    void removeById(int id) throws NoSuchEntryException;
    TicketEntry entryWithId(int id) throws NoSuchEntryException;
    void updateById(int id, Ticket ticket) throws NoSuchEntryException;
}
