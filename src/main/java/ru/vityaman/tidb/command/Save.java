package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.CollectionFromDataset;
import ru.vityaman.tidb.collection.data.TicketDataset;
import ru.vityaman.tidb.file.File;
import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;

/**
 * Represents a 'save' command.
 */
public final class Save implements Executable {
    private final File<TicketDataset> file;
    private final CollectionFromDataset collection;

    public Save(File<TicketDataset> file, CollectionFromDataset collection) {
        this.file = file;
        this.collection = collection;
    }

    private void execute() throws ExecutionException, FileSystemException {
        file.write(collection.dataset());
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        try {
            execute();
        } catch (FileSystemException e) {
            throw new ExecutionException(e);
        }
    }
}
