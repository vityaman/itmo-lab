package ru.vityaman.tidb.command;

import static ru.vityaman.tidb.ui.out.ConsoleColor.BLUE;
import static ru.vityaman.tidb.ui.out.ConsoleColor.WHITE;
import static ru.vityaman.tidb.ui.out.ConsoleColor.YELLOW;

import java.util.List;

import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.ui.out.Out;

/**
 * Represents a 'help' command.
 */
public final class Help implements Executable {
    private final Out out;

    /**
     * @param out where to print out
     */
    public Help(Out out) {
        this.out = out;
    }

    private void execute() {
        printc("help",
              "list available commands");
        printc("history",
              "prints last 11 executed instructions");
        printc("all",
              "prints all tickets");
        printc("exec filepath: string",
              "executes script");
        printc("insert",
              "inserts entered by user ticket");
        printc("insert ticket: Map",
              "inserts provided as argument ticket");
        printc("update id: int, ticket: Map",
              "updates ticket with provided id");
        printc("update id: int",
              "updates ticket entered by user");
        printc("remove id: int",
              "removes ticket with given id");
        printc("clear",
              "deletes all elements in collection");
        printc("remove_id_less_than id: int",
              "removes tickets where id less than provided id");
        printc("group_by_creation_date",
              "prints tickets grouped by creation date");
        printc("filter_greater_than_person",
              "person provided by user");
        printc("filter_greater_than_type",
              "type provided by user");
        printc("pwd",
              "prints current working directory");
        printc("save",
               "saves current collection to json file");
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }

    private void printc(String signature, String description) {
        out.println(
              WHITE.wrapped("| ") +
              BLUE.wrapped(signature) +
              WHITE.wrapped(" --> ") +
              YELLOW.wrapped(description)
        );
    }
}
