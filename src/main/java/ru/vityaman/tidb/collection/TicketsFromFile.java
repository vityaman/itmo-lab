package ru.vityaman.tidb.collection;

import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.data.TicketDataset;
import ru.vityaman.tidb.file.File;
import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.InvalidFileContentException;

public final class TicketsFromFile {
    private final File<TicketDataset> file;
    private final CollectionFromDataset collection;

    public TicketsFromFile(File<TicketDataset> file) 
    throws FileSystemException, InvalidFileContentException {
        this.file = file;
        this.collection = new CollectionFromDataset(file.content());
    }

    public TicketCollection collection() {
        return collection;
    }

    public void save() throws FileSystemException {
        file.write(collection.dataset());
    }
}
