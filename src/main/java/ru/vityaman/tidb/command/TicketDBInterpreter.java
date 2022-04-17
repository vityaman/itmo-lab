package ru.vityaman.tidb.command;

import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.lang.interpreter.*;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Printer;

import java.util.Map;

/**
 * Represents main interpreter with set commands.
 */
public final class TicketDBInterpreter implements Interpreter {
    private final Interpreter origin;

    /**
     * @param in where to get input
     * @param out where to print out
     * @param storage storage to manage
     */
    public TicketDBInterpreter(Input in, Printer out,
                               JsonTicketsStorage storage) {

        SimpleInterpreter simple = new SimpleInterpreter();
        HistoryWriter historyWriter = new HistoryWriter(simple, 11);

        simple.load(Command.of(
                new Help(out),
                "help"));
        simple.load(Command.of(
                new All(out, storage::collection),
                "all"));
        simple.load(Command.of(
                new GetById(out, storage::collection),
                "get", Integer.class));
        simple.load(Command.of(
                new Pwd(out),
                "pwd"));
        simple.load(Command.of(
                new Exec(historyWriter),
                "exec", String.class));
        simple.load(Command.of(
                new InsertArgument(out, storage::collection),
                "insert", Map.class));
        simple.load(Command.of(
                new InsertInteractive(in, out, storage::collection),
                "insert"));
        simple.load(Command.of(
                new UpdateArgument(storage::collection),
                "update", Integer.class, Map.class));
        simple.load(Command.of(
                new UpdateInteractive(in, out, storage::collection),
                "update", Integer.class));
        simple.load(Command.of(
                new Save(in, out, storage),
                "save"));
        simple.load(Command.of(
                new RemoveById(storage::collection),
                "remove", Integer.class));
        simple.load(Command.of(
                new Open(storage),
                "open", String.class));
        simple.load(Command.of(
                new Clear(storage::collection),
                "clear"));
        simple.load(Command.of(
                new Exit(),
                "exit"));
        simple.load(Command.of(
                new History(out, historyWriter::lastExecutedInstructions),
                "history"));
        simple.load(Command.of(
                new RemoveIdLessThan(storage::collection),
                "remove_where_id_less_than", Integer.class
        ));
        simple.load(Command.of(
                new GroupByCreationDate(out, storage::collection),
                "group_by_creation_date"
        ));
        simple.load(Command.of(
                new FilterGreaterThanPersonInteractive(in, out, storage::collection),
                "filter_greater_than_person"
        ));
        simple.load(Command.of(
                new FilterGreaterThanTypeInteractive(in, out, storage::collection),
                "filter_greater_than_type"
        ));

        origin = historyWriter;
    }

    @Override
    public void execute(Instruction instruction) {
        origin.execute(instruction);
    }
}
