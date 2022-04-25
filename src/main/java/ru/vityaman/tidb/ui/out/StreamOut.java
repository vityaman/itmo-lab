package ru.vityaman.tidb.ui.out;

import java.io.PrintStream;

public class StreamOut implements Out {
    private final PrintStream out;

    public StreamOut(PrintStream out) {
        this.out = out;
    }

    @Override
    public Out print(String text) {
        out.print(text);
        return this;
    }
}
