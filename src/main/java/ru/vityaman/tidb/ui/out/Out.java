package ru.vityaman.tidb.ui.out;

import static ru.vityaman.tidb.ui.out.ConsoleColor.RED;

/**
 * Interface represents something where you can print.
 */
public interface Out {
    /**
     * @param text text to print
     * @return
     */
    void print(String text);

    /**
     * Prints text and new line.
     * @param object text to print
     * @see print
     */
    default void println(Object object) {
        print(object.toString() + '\n');
    }

    /**
     * Prints red text.
     * @param object
     */

    default void error(String message) {
        println(RED.wrapped(message));
    }

    default void error(Exception exception) {
        error(exception.getMessage());
    }
}
