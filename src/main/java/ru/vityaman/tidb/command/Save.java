package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'save' command.
 */
public final class Save implements Executable {
    private final JsonTicketsStorage storage;
    private final Out out;

    /**
     * @param storage storage to save
     */
    public Save(JsonTicketsStorage storage, Out out) {
        this.storage = storage;
        this.out = out;
    }

    private void execute() {
        storage.save();
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }
}
