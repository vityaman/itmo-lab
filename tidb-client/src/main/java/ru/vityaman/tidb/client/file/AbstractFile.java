package ru.vityaman.tidb.client.file;

import java.nio.file.Path;

/**
 * AbstractFile.
 */
public abstract class AbstractFile<T> implements File<T> {
    private final Path path;

    public AbstractFile(Path path) {
        this.path = path;
    }

    @Override
    public final Path path() {
        return path;
    }
}
