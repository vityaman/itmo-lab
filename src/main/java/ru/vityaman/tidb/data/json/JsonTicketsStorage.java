package ru.vityaman.tidb.data.json;

import ru.vityaman.tidb.data.file.FutureJsonFile;
import ru.vityaman.tidb.data.file.JsonFile;
import ru.vityaman.tidb.data.resource.TicketsStorage;
import ru.vityaman.tidb.data.resource.exception.SaveInabilityException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Storage of tickets.
 */
public final class JsonTicketsStorage implements TicketsStorage {
    private JsonFile file;
    private JsonTickets tickets;

    public JsonTicketsStorage() {
        this.tickets = new JsonTickets();
    }

    public JsonTicketsStorage(String filepath) {
        open(filepath);
    }

    /**
     * @param filepath collection to open
     */
    public void open(String filepath) {
        this.file = new FutureJsonFile(filepath, new HashMap<String, Object>() {{
            put("nextId", 1);
            put("list", new ArrayList<>());
        }});
        this.tickets = new JsonTickets(file.content());
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
        if (file == null) {
            throw new SaveInabilityException("No save location provided");
        }
        file.write(tickets.json);
    }
}
