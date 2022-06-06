package ru.vityaman.tidb.client.ui;

import java.nio.file.Path;
import java.util.Set;

import ru.vityaman.parsing.utils.ParsingException;
import ru.vityaman.tidb.api.collection.RemoteTicketCollection;
import ru.vityaman.tidb.api.interact.client.Client;
import ru.vityaman.tidb.client.command.*;
import ru.vityaman.tidb.client.lang.Program;
import ru.vityaman.tidb.client.lang.interpreter.*;
import ru.vityaman.tidb.client.lang.interpreter.exception.InterpreterException;
import ru.vityaman.tidb.client.serialization.VionTicketSerializer;
import ru.vityaman.tidb.client.ui.input.EndOfInputException;
import ru.vityaman.tidb.client.ui.input.Input;
import ru.vityaman.tidb.client.ui.out.Out;
import ru.vityaman.vion.VionObject;

public final class TidbConsole implements Runnable {
    private final Input in;
    private final Out out;
    private final Interpreter interpreter;

    public TidbConsole(
            Input in, Out out,
            Client client,
            VionTicketSerializer ticketSerializer,
            Set<Path> trace
    ) {
        this.in = in;
        this.out = out;

        RemoteTicketCollection collection = client.collection();

        ExecuteHistory history = new ExecuteHistory(11);

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
                new History(out, history::lastNInstructions),
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
                    ticketSerializer
                ),
                "insert", 
                String.class, VionObject.class
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
                    ticketSerializer
                ),
                "update", 
                Integer.class, VionObject.class
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
                    client,
                    ticketSerializer,
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
                Instruction instruction = Program.parseInstruction(input);
                interpreter.execute(instruction);
            } catch (ParsingException | InterpreterException e) {
                out.error(e);
            } catch (EndOfInputException e) {
                out.println(String.format("Pokasiki as %s", e.getMessage()));
                break;
            }
        }
    }
}
