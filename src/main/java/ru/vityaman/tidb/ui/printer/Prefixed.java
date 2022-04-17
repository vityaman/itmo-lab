package ru.vityaman.tidb.ui.printer;

public final class Prefixed implements Printer {
    private final Printer origin;
    private final String prefix;

    public Prefixed(String prefix, Printer origin) {
        this.origin = origin;
        this.prefix = prefix;
    }

    @Override
    public void print(String text) {
        origin.print(prefix + text);
    }
}
