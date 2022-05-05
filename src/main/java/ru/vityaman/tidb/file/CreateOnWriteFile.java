package ru.vityaman.tidb.file;

import java.io.IOException;
import java.nio.file.Path;

import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.InvalidFileContentException;

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
    public void write(T content) throws FileSystemException {
        java.io.File file = path().toFile();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new FileSystemException(
                String.format(
                    "Can't create a file %s",
                    file.getPath()
                ),
                e
            );
        }
        origin.write(content);
    }

    @Override
    public T content() throws FileSystemException,
                              InvalidFileContentException {
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
