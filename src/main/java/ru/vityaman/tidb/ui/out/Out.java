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
     * Prints arguments without separator.
     * @param texts
     */
    default void print(String... texts) {
        for (String text : texts) {
            print(text);
        }
    }

    /**
     * Prints text and new line.
     * @param text text to print
     * @see print
     */
    default void println(String text) {
        print(text + '\n');
    }

    default void println(String... texts) {
        for (String text : texts) {
            print(text);
        }
        print("\n");
    }

    /**
     * Prints red text.
     * @param text
     */
    default void error(String text) {
        println(RED.wrapped(text));
    }
}
