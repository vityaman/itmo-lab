package ru.vityaman.tidb.ui.printer;

public final class Colored implements Printer {
    private final Printer origin;
    private final ConsoleColor color;

    public Colored(ConsoleColor color, Printer origin) {
        this.origin = origin;
        this.color = color;
    }

    @Override
    public void print(String text) {
        origin.print(color.wrapped(text));
    }
}
