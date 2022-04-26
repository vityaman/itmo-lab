package ru.vityaman.tidb.ui.out;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Out using StreamWriter.
 */
public class StreamOut implements Out {
    private final PrintStream out;

    public StreamOut(PrintStream out) {
        this.out = out;
    }

    @Override
    public void print(String text) {
        out.print(text);
    }
}
