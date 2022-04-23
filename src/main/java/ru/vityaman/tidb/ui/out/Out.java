package ru.vityaman.tidb.ui.out;

import static ru.vityaman.tidb.ui.out.ConsoleColor.RED;

public interface Out {
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
