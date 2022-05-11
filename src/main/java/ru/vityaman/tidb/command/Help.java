package ru.vityaman.tidb.command;

import static ru.vityaman.tidb.ui.out.ConsoleColor.BLUE;
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
        printc("exec \"filepath\"",
              "executes script");
        printc("insert \"key\"",
              "inserts entered by user ticket");
        printc("insert \"key\", {ticket}",
              "inserts provided as argument ticket");
        printc("update id, {ticket}",
              "updates ticket with provided id");
        printc("update id",
              "updates ticket entered by user");
        printc("clear",
              "deletes all elements in collection");
        printc("remove_key_less_than \"key\"",
              "removes tickets where key less than provided key");
        printc("group_by_creation_date",
              "prints tickets grouped by creation date");
        printc("count_greater_than_person {person}",
              "person provided by user");
      printc("remove_less_than {ticket}",
             "removes tickets, ticket provided by user");
        printc("filter_greater_than_type \"type\"",
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
        out.println(String.format(
            "| %-45s%s", 
            BLUE.wrapped(signature.toString()),
            YELLOW.wrapped(description.toString())
        ));
    }
}
