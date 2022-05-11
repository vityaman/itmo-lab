package ru.vityaman.tidb.ui;

import java.nio.file.Path;
import java.util.Set;

import ru.vityaman.tidb.collection.TicketsFromFile;
import ru.vityaman.tidb.collection.base.TicketCollection;
import ru.vityaman.tidb.collection.json.serialize.TicketSerialization;
import ru.vityaman.tidb.command.All;
import ru.vityaman.tidb.command.Clear;
import ru.vityaman.tidb.command.CountGreaterThanPersonInteractive;
import ru.vityaman.tidb.command.Exec;
import ru.vityaman.tidb.command.ExecScript;
import ru.vityaman.tidb.command.Exit;
import ru.vityaman.tidb.command.FilterGreaterThanTypeInteractive;
import ru.vityaman.tidb.command.GroupByCreationDate;
import ru.vityaman.tidb.command.Help;
import ru.vityaman.tidb.command.History;
import ru.vityaman.tidb.command.InsertArgument;
import ru.vityaman.tidb.command.InsertInteractive;
import ru.vityaman.tidb.command.Pwd;
import ru.vityaman.tidb.command.RemoveKeyLessThan;
import ru.vityaman.tidb.command.RemoveLowerThanInteractive;
import ru.vityaman.tidb.command.Save;
import ru.vityaman.tidb.command.UpdateByIdArgument;
import ru.vityaman.tidb.command.UpdateByIdInteractive;
import ru.vityaman.tidb.lang.interpreter.Command;
import ru.vityaman.tidb.lang.interpreter.HistoryWriter;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.Interpreter;
import ru.vityaman.tidb.lang.interpreter.LimitedQueue;
import ru.vityaman.tidb.lang.interpreter.RecursionControlInterpreter;
import ru.vityaman.tidb.lang.interpreter.SimpleInterpreter;
import ru.vityaman.tidb.lang.interpreter.exception.InterpreterException;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.parse.Parse;
import ru.vityaman.tidb.lang.parse.ParsingException;
import ru.vityaman.tidb.ui.input.EndOfInputException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Out;

public final class TidbConsole implements Runnable {
    private final Input in;
    private final Out out;
    private final Interpreter interpreter;

    public TidbConsole(
        Input in, Out out, 
        TicketsFromFile tickets,
        TicketSerialization ticketSerialization,
        Set<Path> trace
    ) {
        this.in = in;
        this.out = out;

        TicketCollection collection = tickets.collection();

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
                new Save(tickets), 
                "save"
            ),
            Command.of(
                new CountGreaterThanPersonInteractive(
                    in, out, collection
                ),
                "count_greater_than_person"
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
                new RemoveLowerThanInteractive(in, out, collection), 
                "remove_lower_than"
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

        Interpreter main = new RecursionControlInterpreter(
            new HistoryWriter(simple, history)
        );

        simple.load(
            Command.of(
                new Exec(out, main),
                "exec", 
                String.class
            )
        );

        simple.load(
            Command.of(
                new ExecScript(
                    out, 
                    tickets, 
                    ticketSerialization,
                    trace
                ), 
                "exec_script", 
                String.class
            )
        );

        interpreter = main;
    }

    @Override
    public void run() {
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
