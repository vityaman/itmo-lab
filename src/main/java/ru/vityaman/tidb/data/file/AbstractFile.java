package ru.vityaman.tidb.data.file;

public abstract class AbstractFile<T> implements File<T> {
    private final java.io.File origin;

    public AbstractFile(java.io.File origin) {
        this.origin = origin;
    }

    @Override
    public final java.io.File origin() {
        return origin;
    }
}
