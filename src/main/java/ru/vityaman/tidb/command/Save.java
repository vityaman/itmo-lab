package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.data.file.exception.FileSystemException;
import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;

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

    private void execute() throws ExecutionException {
        try {
            storage.save();
        } catch (FileSystemException e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        execute();
    }
}
