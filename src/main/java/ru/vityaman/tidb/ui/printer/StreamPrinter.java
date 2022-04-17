package ru.vityaman.tidb.ui.printer;

import java.io.PrintStream;

public class StreamPrinter implements Printer {
    private final PrintStream out;

    public StreamPrinter(PrintStream out) {
        this.out = out;
    }

    @Override
    public void print(String text) {
        out.print(text);
    }
}
