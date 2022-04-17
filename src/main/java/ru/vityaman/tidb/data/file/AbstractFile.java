package ru.vityaman.tidb.data.file;

import java.io.File;

/**
 * Basement for files.
 */
public abstract class AbstractFile {

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
}