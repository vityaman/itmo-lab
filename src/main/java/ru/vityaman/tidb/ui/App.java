package ru.vityaman.tidb.ui;

import java.nio.file.Path;
import java.text.SimpleDateFormat;

import ru.vityaman.tidb.collection.CollectionFromDataset;
import ru.vityaman.tidb.collection.data.TicketDataset;
import ru.vityaman.tidb.collection.data.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.json.serialize.CoordinatesSerialization;
import ru.vityaman.tidb.collection.json.serialize.DatasetSerialization;
import ru.vityaman.tidb.collection.json.serialize.LocationSerialization;
import ru.vityaman.tidb.collection.json.serialize.PersonSerialization;
import ru.vityaman.tidb.collection.json.serialize.TicketEntrySerialization;
import ru.vityaman.tidb.collection.json.serialize.TicketSerialization;
import ru.vityaman.tidb.command.All;
import ru.vityaman.tidb.command.Clear;
import ru.vityaman.tidb.command.Exec;
import ru.vityaman.tidb.command.Exit;
import ru.vityaman.tidb.command.FilterGreaterThanPersonInteractive;
import ru.vityaman.tidb.command.FilterGreaterThanTypeInteractive;
import ru.vityaman.tidb.command.GroupByCreationDate;
import ru.vityaman.tidb.command.Help;
import ru.vityaman.tidb.command.History;
import ru.vityaman.tidb.command.InsertArgument;
import ru.vityaman.tidb.command.InsertInteractive;
import ru.vityaman.tidb.command.Pwd;
import ru.vityaman.tidb.command.RemoveKeyLessThan;
import ru.vityaman.tidb.command.Save;
import ru.vityaman.tidb.command.UpdateByIdArgument;
import ru.vityaman.tidb.command.UpdateByIdInteractive;
import ru.vityaman.tidb.file.CreateOnWriteFile;
import ru.vityaman.tidb.file.File;
import ru.vityaman.tidb.file.JsonFile;
import ru.vityaman.tidb.file.TicketDatasetFile;
import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.InvalidFileContentException;
import ru.vityaman.tidb.lang.interpreter.Command;
import ru.vityaman.tidb.lang.interpreter.HistoryWriter;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.Interpreter;
import ru.vityaman.tidb.lang.interpreter.LimitedQueue;
import ru.vityaman.tidb.lang.interpreter.SimpleInterpreter;
import ru.vityaman.tidb.lang.interpreter.exception.InterpreterException;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.parse.Parse;
import ru.vityaman.tidb.lang.parse.ParsingException;
import ru.vityaman.tidb.ui.input.EndOfInputException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Ticket DB App.
 */
public final class App implements Runnable {
    private final Out out;
    private final Input in;
    private final Path path;

    private final Interpreter interpreter;

    /**
     * @param out where to print
     * @param in where to input
     * @param path filepath to open
     * @throws InvalidFileContentException
     * @throws FileSystemException
     * @throws InvalidFieldValueException
     */
    public App(Out out, Input in, Path path) 
    throws FileSystemException, InvalidFileContentException 
    {
        this.out = out;
        this.in = in;
        this.path = path;

        TicketSerialization ticketSerialization 
            = new TicketSerialization(
                new CoordinatesSerialization(), 
                new PersonSerialization(
                    new LocationSerialization()
                )
            );

        File<TicketDataset> file = new CreateOnWriteFile<TicketDataset>(
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
        );

        CollectionFromDataset collection = new CollectionFromDataset(
            file.content()
        );

        LimitedQueue<Instruction> history = new LimitedQueue<>(11);

        SimpleInterpreter simple = new SimpleInterpreter(
            Command.of(
                new Help(out), 
                "help"
            ),
            Command.of(
                new All(out, collection), 
                "all"
            ),
            Command.of(
                new Clear(collection), 
                "clear"
            ),
            Command.of(
                new History(out, history::elements), 
                "history"
            ),
            Command.of(
                new Pwd(out), 
                "pwd"
            ),
            Command.of(
                new Exit(), 
                "exit"
            ),
            Command.of(
                new Save(file, collection), 
                "save"
            ),
            Command.of(
                new FilterGreaterThanPersonInteractive(
                    in, out, collection
                ),
                "filter_greater_than_person"
            ),
            Command.of(
                new FilterGreaterThanTypeInteractive(
                    in, out, collection
                ),
                "filter_greater_than_type"
            ),
            Command.of(
                new GroupByCreationDate(out, collection),
                "group_by_creation_date"
            ),
            Command.of(
                new InsertArgument(
                    out, 
                    collection, 
                    ticketSerialization
                ),
                "insert", 
                String.class, JsonObject.class
            ),
            Command.of(
                new InsertInteractive(in, out, collection),
                "insert",
                String.class
            ),
            Command.of(
                new RemoveKeyLessThan(collection),
                "remove_key_less_than", 
                String.class
            ),
            Command.of(
                new UpdateByIdArgument(
                    collection, 
                    ticketSerialization
                ),
                "update", 
                Integer.class, JsonObject.class
            ),
            Command.of(
                new UpdateByIdInteractive(
                    in, out, collection
                ),
                "update",
                Integer.class
            )
        );

        HistoryWriter historyWriter = new HistoryWriter(simple, history);

        simple.load(
            Command.of(
                new Exec(out, historyWriter),
                "exec", 
                String.class
            )
        );

        interpreter = historyWriter;
    }

    @Override
    public void run() {
        out.println(
            "Welcome! Print 'help' to list all command!" +
            " Opened file is '" + path + "'.");
        while (true) {
            try {
                out.print("> ");
                String input = in.readLine() + ' ';
                Instruction instruction = Parse.instruction(input);
                interpreter.execute(instruction);
            } catch (ParsingException e) {
                out.error(e);
            } catch (InterpreterException e) {
                out.error(e);
            } catch (EndOfInputException e) {
                out.println(String.format("Pokasiki as %s", e.getMessage()));
                break;
            }
        }
    }
}
