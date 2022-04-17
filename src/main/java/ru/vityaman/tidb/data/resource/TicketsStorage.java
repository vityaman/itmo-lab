package ru.vityaman.tidb.data.resource;

public interface TicketsStorage {
    Tickets collection();
    void save();
}
