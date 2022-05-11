package ru.vityaman.tidb.ui;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import ru.vityaman.tidb.collection.TicketsFromFile;
import ru.vityaman.tidb.collection.data.TicketDataset;
import ru.vityaman.tidb.collection.json.serialize.CoordinatesSerialization;
import ru.vityaman.tidb.collection.json.serialize.DatasetSerialization;
import ru.vityaman.tidb.collection.json.serialize.LocationSerialization;
import ru.vityaman.tidb.collection.json.serialize.PersonSerialization;
import ru.vityaman.tidb.collection.json.serialize.TicketEntrySerialization;
import ru.vityaman.tidb.collection.json.serialize.TicketSerialization;
import ru.vityaman.tidb.file.CreateOnWriteFile;
import ru.vityaman.tidb.file.JsonFile;
import ru.vityaman.tidb.file.TicketDatasetFile;
import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.InvalidFileContentException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Ticket DB App.
 */
public final class App implements Runnable {
    private final Out out;
    private final Path path;

    private final TidbConsole interpreter;

    /**
     * @param out where to print
     * @param in where to input
     * @param path filepath to open
     * @throws InvalidFileContentException
     * @throws FileSystemException
     */
    public App(Out out, Input in, Path path)
    throws FileSystemException, InvalidFileContentException 
    {
        this.out = out;
        this.path = path;

        TicketSerialization ticketSerialization = 
            new TicketSerialization(
                new CoordinatesSerialization(), 
                new PersonSerialization(
                    new LocationSerialization()
                )
            );

        interpreter = new TidbConsole(
            in, 
            out, 
            new TicketsFromFile(
                new CreateOnWriteFile<TicketDataset>(
                    new TicketDatasetFile(
                        new JsonFile(path),
                        new DatasetSerialization(
                            new TicketEntrySerialization(
                                ticketSerialization,
                                new SimpleDateFormat("dd.MM.yyyy hh:mm:ss")
                            )
                        )                
                    ),
                    // default // TODO: bad for reading
                    new TicketDataset()
                )
            ),
            ticketSerialization,
            new HashSet<>()
        );
    }

    @Override
    public void run() {
        out.println(
            "Welcome! Print 'help' to list all command!" +
            " Opened file is '" + path + "'.");
        interpreter.run();
    }
}
