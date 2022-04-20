package ru.vityaman.tidb;

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
import ru.vityaman.tidb.command.exception.CommandException;
import ru.vityaman.tidb.data.file.exception.FileAccessException;
import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.data.resource.Tickets;
import ru.vityaman.tidb.data.resource.exception.ResourceException;
import ru.vityaman.tidb.lang.interpreter.Command;
import ru.vityaman.tidb.lang.interpreter.HistoryWriter;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.LimitedQueue;
import ru.vityaman.tidb.lang.interpreter.SimpleInterpreter;
import ru.vityaman.tidb.lang.parse.Parse;
import ru.vityaman.tidb.lang.parse.ParsingException;
import ru.vityaman.tidb.ui.input.EndOfInputException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.input.StreamInput;
import ru.vityaman.tidb.ui.printer.Printer;
import ru.vityaman.tidb.ui.printer.StreamPrinter;

public class App {

    public static void main(String[] args) {
        Printer out = new StreamPrinter(System.out);
        Input in = new StreamInput(System.in);

        String filepath = "untitled.jso";
        try {
            filepath = System.getProperty("TIDB_FILE", args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            out.error("No filepath provided");
            System.exit(0);
        }


        JsonTicketsStorage storage
            = new JsonTicketsStorage(filepath);
        Tickets tickets = storage.collection();

        LimitedQueue<Instruction> history = new LimitedQueue<>(11);

        SimpleInterpreter simple = new SimpleInterpreter(
            Command.of(new Help(out), "help"),
            Command.of(new All(out, tickets), "all"),
            Command.of(new Clear(tickets), "clear"),
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
            Command.of(new RemoveIdLessThan(tickets),
                "remove_id_less_than", Integer.class),
            Command.of(new UpdateArgument(tickets),
                "update", Integer.class, Map.class),
            Command.of(new UpdateInteractive(in, out, tickets),
                "update", Integer.class)
        );

        HistoryWriter historyWriter = new HistoryWriter(simple, history);

        simple.load(Command.of(new Exec(historyWriter),
                    "exec", String.class));

        while (true) {
            try {
                out.print("> ");
                String input = in.readLine() + ' ';
                Instruction instruction = Parse.instruction(input);
                historyWriter.execute(instruction);
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
