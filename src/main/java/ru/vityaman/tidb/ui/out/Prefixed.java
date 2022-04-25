package ru.vityaman.tidb.ui.out;

public final class Prefixed implements Out {
    private final Out origin;
    private final String prefix;

    public Prefixed(String prefix, Out origin) {
        this.origin = origin;
        this.prefix = prefix;
    }

    @Override
    public Out print(String text) {
        origin.print(prefix + text);
        return this;
    }
}
