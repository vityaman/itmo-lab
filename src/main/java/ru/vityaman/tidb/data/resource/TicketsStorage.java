package ru.vityaman.tidb.data.resource;

import java.io.File;

public interface TicketsStorage {
    void open(File file);
    Tickets collection();
    void save();
    void save(File file);
}
