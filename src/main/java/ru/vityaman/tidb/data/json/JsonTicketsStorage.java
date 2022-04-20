package ru.vityaman.tidb.data.json;

import ru.vityaman.tidb.data.file.JsonFile;
import ru.vityaman.tidb.data.resource.TicketsStorage;
import ru.vityaman.tidb.data.resource.exception.SaveInabilityException;

/**
 * Storage of tickets.
 */
public final class JsonTicketsStorage implements TicketsStorage {
    private JsonFile file;
    private JsonTickets tickets;

    public JsonTicketsStorage(String filepath) {
        file = new JsonFile(filepath);
        tickets = new JsonTickets(file.content());
    }

    @Override
    public JsonTickets collection() {
        return tickets;
    }

    /**
     * Saves or throws SaveInabilityException if no fil provided.
     * @see SaveInabilityException
     */
    @Override
    public void save() {
        file.write(tickets.json);
    }
}
