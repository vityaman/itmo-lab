package ru.vityaman.tidb.client.ui.out;


import static ru.vityaman.tidb.client.ui.out.ConsoleColor.RED;

/**
 * Interface represents something where you can print.
 */
public interface Out {
    void print(String text);

    default void println(Object object) {
        print(object.toString() + '\n');
    }


    default void error(String message) {
        println(RED.wrapped(message));
    }

    default void error(Exception exception) {
        error(exception.getMessage());
    }
}
