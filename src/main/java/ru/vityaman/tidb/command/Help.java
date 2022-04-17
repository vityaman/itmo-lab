package ru.vityaman.tidb.command;

import ru.vityaman.tidb.ui.printer.Printer;

import java.util.List;

import static ru.vityaman.tidb.ui.printer.ConsoleColor.*;

/**
 * Represents a 'help' command.
 */
public final class Help implements Executable {
    private final Printer out;

    /**
     * @param out where to print out
     */
    public Help(Printer out) {
        this.out = out;
    }

    @Override
    public void execute(List<Object> args) {
        printc(
                "help",
                "list available commands"
        );
        printc(
                "get id: int",
                "prints ticket with given id"
        );
        printc(
                "all",
                "prints all tickets"
        );
        printc(
                "exec filepath: string",
                "executes script"
        );
        printc(
                "insert",
                "inserts entered by user ticket"
        );
        printc(
                "insert ticket: Map",
                "inserts provided as argument ticket"
        );
        printc(
                "update id: int, ticket: Map",
                "updates ticket with provided id"
        );
        printc(
                "update id: int",
                "updates ticket entered by user"
        );
        printc(
                "remove id: int",
                "removes ticket with given id"
        );
        printc(
                "open filepath: string",
                "opens file or creates it if does not exist"
        );
        printc(
                "clear",
                "deletes all elements in collection"
        );
        printc(
                "remove_where_id_less_than id: int",
                "removes tickets where id less than provided id"
        );
        printc(
                "group_by_creation_date",
                "prints tickets grouped by creation date"
        );
        printc(
                "filter_greater_than_person",
                "person provided by user"
        );
        printc(
                "filter_greater_than_type",
                "type provided by user"
        );
        printc(
                "pwd",
                "prints current working directory"
        );
        printc(
                "save",
                "saves current collection to json file"
        );
    }

    private void printc(String signature, String description) {
        out.println(
                WHITE.wrapped("> "),
                BLUE.wrapped(signature),
                WHITE.wrapped(" - "),
                YELLOW.wrapped(description)
        );
    }
}
