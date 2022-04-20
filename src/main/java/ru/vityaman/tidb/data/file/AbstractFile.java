package ru.vityaman.tidb.data.file;

import java.io.File;

/**
 * Basement for files.
 */
public abstract class AbstractFile<T> {

    /**
     * File to work with.
     */
    protected final File origin;

    public AbstractFile(File origin) {
        this.origin = origin;
    }

    public AbstractFile(String path) {
        this.origin = new File(path);
    }

    public abstract void write(T content);

    public abstract T content();
}