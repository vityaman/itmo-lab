package ru.vityaman.tidb.ui;

import java.util.Map;

import ru.vityaman.tidb.command.All;
import ru.vityaman.tidb.command.Clear;
import ru.vityaman.tidb.command.DumpArgument;
import ru.vityaman.tidb.command.Exec;
import ru.vityaman.tidb.command.Exit;
import ru.vityaman.tidb.command.FilterGreaterThanPersonInteractive;
import ru.vityaman.tidb.command.FilterGreaterThanTypeInteractive;
import ru.vityaman.tidb.command.GetById;
import ru.vityaman.tidb.command.GroupByCreationDate;
import ru.vityaman.tidb.command.Help;
import ru.vityaman.tidb.command.History;
import ru.vityaman.tidb.command.InsertArgument;
import ru.vityaman.tidb.command.InsertInteractive;
import ru.vityaman.tidb.command.Open;
import ru.vityaman.tidb.command.Pwd;
import ru.vityaman.tidb.command.RemoveById;
import ru.vityaman.tidb.command.RemoveIdLessThan;
import ru.vityaman.tidb.command.Save;
import ru.vityaman.tidb.command.UpdateArgument;
import ru.vityaman.tidb.command.UpdateInteractive;
import ru.vityaman.tidb.command.exception.CommandException;
import ru.vityaman.tidb.data.file.exception.FileAccessException;
import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.data.resource.exception.ResourceException;
import ru.vityaman.tidb.lang.interpreter.Command;
import ru.vityaman.tidb.lang.interpreter.HistoryWriter;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.Interpreter;
import ru.vityaman.tidb.lang.interpreter.LimitedQueue;
import ru.vityaman.tidb.lang.interpreter.SimpleInterpreter;
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
    private final Interpreter interpreter;

    private final String initialFilePath;

    /**
     * @param out where to print
     * @param in where to input
     * @param initialFilePath filepath to open
     */
    public App(Out out, Input in, String initialFilePath) {
        this.out = out;
        this.in = in;
        this.initialFilePath = initialFilePath;

        JsonTicketsStorage storage
            = new JsonTicketsStorage(new java.io.File(initialFilePath));
        LimitedQueue<Instruction> history = new LimitedQueue<>(11);

        SimpleInterpreter simple = new SimpleInterpreter(
            Command.of(new Help(out), "help"),
            Command.of(new All(out, storage::collection), "all"),
            Command.of(new Clear(storage::collection), "clear"),
            Command.of(new History(out, history::elements), "history"),
            Command.of(new Open(storage), "open", String.class),
            Command.of(new Pwd(out), "pwd"),
            Command.of(new Exit(), "exit"),
            Command.of(new Save(storage), "save"),
            Command.of(new DumpArgument(storage), "dump", String.class),
            Command.of(
                new FilterGreaterThanPersonInteractive(in, out, storage::collection),
                "filter_greater_than_person"),
            Command.of(
                new FilterGreaterThanTypeInteractive(in, out, storage::collection),
                "filter_greater_than_type"),
            Command.of(new GetById(out, storage::collection), "get", Integer.class),
            Command.of(new GroupByCreationDate(out, storage::collection),
                "group_by_creation_date"),
            Command.of(new InsertArgument(out, storage::collection),
                "insert", Map.class),
            Command.of(new InsertInteractive(in, out, storage::collection),"insert"),
            Command.of(new RemoveById(storage::collection), "remove", Integer.class),
            Command.of(new RemoveIdLessThan(storage::collection),
                "remove_id_less_than", Integer.class),
            Command.of(new UpdateArgument(storage::collection),
                "update", Integer.class, Map.class),
            Command.of(new UpdateInteractive(in, out, storage::collection),
                "update", Integer.class)
        );

        HistoryWriter historyWriter = new HistoryWriter(simple, history);

        simple.load(Command.of(new Exec(historyWriter),
                    "exec", String.class));

        interpreter = historyWriter;
    }

    @Override
    public void run() {
        out.println(
            "Welcome! Print 'help' to list all command!" +
            " Opened file is " + initialFilePath + ".");
        while (true) {
            try {
                out.print("> ");
                String input = in.readLine() + ' ';
                Instruction instruction = Parse.instruction(input);
                interpreter.execute(instruction);
            } catch (ParsingException | FileAccessException
                   | CommandException | ResourceException e) {
                out.error("Error: " + e.getMessage());
            } catch (EndOfInputException e) {
                out.println(String.format("Pokasiki as %s", e.getMessage()));
                break;
            }
        }
    }
}
