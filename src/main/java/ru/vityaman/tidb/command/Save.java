package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.lang.interpreter.Executable;

/**
 * Represents a 'save' command.
 */
public final class Save implements Executable {
    private final JsonTicketsStorage storage;

    /**
     * @param storage storage to save
     */
    public Save(JsonTicketsStorage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(List<Object> args) {
        storage.save();
    }
}
