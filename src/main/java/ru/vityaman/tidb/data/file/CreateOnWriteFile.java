package ru.vityaman.tidb.data.file;

import java.io.IOException;

import ru.vityaman.tidb.data.file.exception.FileSystemException;

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
    public void write(T content) {
        java.io.File file = origin();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new FileSystemException(
                String.format("Can't create a file %s", file.getPath()), e);
        }
        origin.write(content);
    }

    @Override
    public T content() {
        if (origin().exists()) {
            return origin.content();
        }
        return defaultContent;
    }

    @Override
    public java.io.File origin() {
        return origin.origin();
    }
}
