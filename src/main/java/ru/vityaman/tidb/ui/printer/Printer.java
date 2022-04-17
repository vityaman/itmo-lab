package ru.vityaman.tidb.ui.printer;

import static ru.vityaman.tidb.ui.printer.ConsoleColor.RED;

public interface Printer {
    void print(String text);

    default void print(String... texts) {
        for (String text : texts) {
            print(text);
        }
    }

    default void println(String text) {
        print(text + '\n');
    }

    default void println(String... texts) {
        for (String text : texts) {
            print(text);
        }
        print("\n");
    }

    default void error(String text) {
        println(RED.wrapped(text));
    }
}
