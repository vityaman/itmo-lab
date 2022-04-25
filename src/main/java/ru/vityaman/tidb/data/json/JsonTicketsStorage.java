package ru.vityaman.tidb.data.json;

import java.util.Map;

import ru.vityaman.tidb.data.file.CreateOnWriteFile;
import ru.vityaman.tidb.data.file.File;
import ru.vityaman.tidb.data.file.JsonFile;
import ru.vityaman.tidb.data.file.JsonTicketsFile;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.data.resource.TicketsStorage;

/**
 * Storage of tickets.
 */
public final class JsonTicketsStorage implements TicketsStorage {
    private File<JsonTickets> file;
    private JsonTickets tickets;

    public JsonTicketsStorage(java.io.File file) {
        open(file);
    }

    @Override
    public void open(java.io.File file) {
        this.file = new JsonTicketsFile(file);
        tickets = this.file.content();
    }

    @Override
    public JsonTickets collection() {
        return tickets;
    }

    @Override
    public void save() {
        file.write(tickets);
    }

    @Override
    public void save(java.io.File file) {
        new JsonTicketsFile(file).write(tickets);
    }

    public boolean isFileExist() {
        return file.origin().exists();
    }
}
