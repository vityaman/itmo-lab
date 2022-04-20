package ru.vityaman.tidb;

import ru.vityaman.tidb.command.TicketDBInterpreter;
import ru.vityaman.tidb.command.exception.CommandException;
import ru.vityaman.tidb.data.file.exception.FileAccessException;
import ru.vityaman.tidb.data.json.JsonTicketsStorage;
import ru.vityaman.tidb.data.resource.exception.ResourceException;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.Interpreter;
import ru.vityaman.tidb.lang.parse.Parse;
import ru.vityaman.tidb.lang.parse.ParsingException;
import ru.vityaman.tidb.ui.input.EndOfInputException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.input.StreamInput;
import ru.vityaman.tidb.ui.printer.Printer;
import ru.vityaman.tidb.ui.printer.StreamPrinter;

// TODO: instructions [update 123 {}] == [update 123, {}]

public class App {

    public static void main(String[] args) {
        Printer out = new StreamPrinter(System.out);
        Input in = new StreamInput(System.in);

        System.out.println(System.getProperty("user.dir"));

        JsonTicketsStorage storage = new JsonTicketsStorage(
            "res/sample.json");

        Interpreter interpreter = new TicketDBInterpreter(in, out, storage);

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
