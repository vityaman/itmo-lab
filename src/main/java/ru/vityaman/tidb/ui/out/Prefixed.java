package ru.vityaman.tidb.ui.out;

/**
 * Out that prints everithing prefixed.
 */
public final class Prefixed implements Out {
    private final Out origin;
    private final String prefix;

    /**
     * @param prefix
     * @param origin parent.
     */
    public Prefixed(String prefix, Out origin) {
        this.origin = origin;
        this.prefix = prefix;
    }

    @Override
    public void print(String text) {
        origin.print(prefix + text);
    }
}
