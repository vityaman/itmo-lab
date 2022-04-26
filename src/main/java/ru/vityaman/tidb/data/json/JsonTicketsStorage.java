package ru.vityaman.tidb.data.json;

import java.nio.file.Path;

import ru.vityaman.tidb.data.file.File;
import ru.vityaman.tidb.data.file.JsonTicketsFile;
import ru.vityaman.tidb.data.file.exception.FileSystemException;
import ru.vityaman.tidb.data.file.exception.InvalidFileContentException;
import ru.vityaman.tidb.data.resource.TicketsStorage;

/**
 * Storage of tickets.
 */
public final class JsonTicketsStorage implements TicketsStorage {
    private File<JsonTickets> file;
    private JsonTickets tickets;

    public JsonTicketsStorage(Path path) throws FileSystemException,
                                                InvalidFileContentException {
        file = new JsonTicketsFile(path);
        tickets = file.content();
    }

    @Override
    public JsonTickets collection() {
        return tickets;
    }

    @Override
    public void save() throws FileSystemException {
        file.write(tickets);
    }
}
