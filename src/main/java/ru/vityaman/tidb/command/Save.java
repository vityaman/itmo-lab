package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.collection.TicketsFromFile;
import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;

/**
 * Represents a 'save' command.
 */
public final class Save implements Executable {
    private final TicketsFromFile tickets;

    public Save(TicketsFromFile tickets) {
        this.tickets = tickets;
    }

    private void execute() 
    throws ExecutionException, FileSystemException {
        tickets.save();
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
