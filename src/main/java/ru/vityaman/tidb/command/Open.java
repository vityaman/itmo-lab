package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.json.JsonTicketsStorage;

import java.util.List;

/**
 * Represents 'open' command.
 */
public final class Open implements Executable {
    private final JsonTicketsStorage storage;

    /**
     * @param storage storage to manage
     */
    public Open(JsonTicketsStorage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(List<Object> args) {
        String filepath = (String) args.get(0);
        storage.open(filepath);
    }
}
