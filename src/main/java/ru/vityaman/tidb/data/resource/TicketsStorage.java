package ru.vityaman.tidb.data.resource;

import ru.vityaman.tidb.data.file.exception.FileSystemException;

public interface TicketsStorage {
    Tickets collection();
    void save() throws FileSystemException;
}
