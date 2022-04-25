package ru.vityaman.tidb.ui.out;

public final class Colored implements Out {
    private final Out origin;
    private final ConsoleColor color;

    public Colored(ConsoleColor color, Out origin) {
        this.origin = origin;
        this.color = color;
    }

    @Override
    public Out print(String text) {
        origin.print(color.wrapped(text));
        return this;
    }
}
