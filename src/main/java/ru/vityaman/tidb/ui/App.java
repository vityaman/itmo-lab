package ru.vityaman.tidb.ui;

import java.nio.file.Path;
import java.util.Map;

import ru.vityaman.tidb.command.All;
import ru.vityaman.tidb.command.Clear;
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
import ru.vityaman.tidb.command.Pwd;
import ru.vityaman.tidb.command.RemoveById;
import ru.vityaman.tidb.command.RemoveIdLessThan;
import ru.vityaman.tidb.command.Save;
import ru.vityaman.tidb.command.UpdateArgument;
import ru.vityaman.tidb.command.UpdateInteractive;
import ru.vityaman.tidb.data.file.exception.FileSystemException;
import ru.vityaman.tidb.data.file.exception.InvalidFileContentException;
import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.lang.interpreter.Command;
import ru.vityaman.tidb.lang.interpreter.HistoryWriter;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.Interpreter;
import ru.vityaman.tidb.lang.interpreter.LimitedQueue;
import ru.vityaman.tidb.lang.interpreter.SimpleInterpreter;
import ru.vityaman.tidb.lang.interpreter.exception.InterpreterException;
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
     */
    public App(Out out, Input in, Path path) throws FileSystemException,
                                                    InvalidFileContentException {
        this.out = out;
        this.in = in;
        this.path = path;

        JsonTicketsStorage storage
            = new JsonTicketsStorage(path);
        Tickets tickets = storage.collection();
        LimitedQueue<Instruction> history = new LimitedQueue<>(11);

        SimpleInterpreter simple = new SimpleInterpreter(
            Command.of(new Help(out), "help"),
            Command.of(new All(out, tickets), "all"),
            Command.of(new Clear(out, tickets), "clear"),
            Command.of(new History(out, history::elements), "history"),
            Command.of(new Pwd(out), "pwd"),
            Command.of(new Exit(), "exit"),
            Command.of(new Save(storage), "save"),
            Command.of(
                new FilterGreaterThanPersonInteractive(in, out, tickets),
                "filter_greater_than_person"),
            Command.of(
                new FilterGreaterThanTypeInteractive(in, out, tickets),
                "filter_greater_than_type"),
            Command.of(new GetById(out, tickets), "get", Integer.class),
            Command.of(new GroupByCreationDate(out, tickets),
                "group_by_creation_date"),
            Command.of(new InsertArgument(out, tickets),
                "insert", Map.class),
            Command.of(new InsertInteractive(in, out, tickets),"insert"),
            Command.of(new RemoveById(tickets), "remove", Integer.class),
            Command.of(new RemoveIdLessThan(out, tickets),
                "remove_id_less_than", Integer.class),
            Command.of(new UpdateArgument(tickets),
                "update", Integer.class, Map.class),
            Command.of(new UpdateInteractive(in, out, tickets),
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
