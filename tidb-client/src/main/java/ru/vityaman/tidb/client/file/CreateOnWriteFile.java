package ru.vityaman.tidb.client.file;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Decorator that creates file on writing or returns default value
 * if it does not exist.
 */
public final class CreateOnWriteFile<T> implements File<T> {
    private final File<T> origin;
    private final T defaultContent;

    public CreateOnWriteFile(File<T> origin, T defaultContent) {
        this.origin = origin;
        this.defaultContent = defaultContent;
    }

    public CreateOnWriteFile(File<T> origin) {
        this(origin, null);
    }

    @Override
    public void write(T content) throws IOException {
        java.io.File file = path().toFile();
        file.createNewFile();
        origin.write(content);
    }

    @Override
    public T content() throws IOException {
        if (path().toFile().exists()) {
            return origin.content();
        }
        return defaultContent;
    }

    @Override
    public Path path() {
        return origin.path();
    }
}
