package ru.vityaman.tidb.data.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import ru.vityaman.tidb.data.file.exception.FileSystemException;
import ru.vityaman.tidb.data.file.exception.InvalidFileContentException;
import ru.vityaman.tidb.data.file.exception.UncheckedFileSystemException;


public final class TextFile extends AbstractFile<String> {
    private static final long DEFAULT_CHAR_LIMIT = 10000L;

    private final long charLimit;

    public TextFile(Path file, long charLimit) {
        super(file);
        this.charLimit = charLimit;
    }

    public TextFile(Path file) {
        this(file, defaultCharLimit());
    }

    @Override
    public void write(String content) throws FileSystemException {
        try (BufferedOutputStream file =
                new BufferedOutputStream(
                    new FileOutputStream(
                        path().toFile()))
        ) {
            file.write(content.getBytes());
        } catch (IOException e) {
            throw new FileSystemException(
                String.format(
                    "Can't write file as %s",
                    e.getMessage()
                ),
                e
            );
        }
    }

    @Override
    public String content() throws FileSystemException,
                                   InvalidFileContentException {
        StringBuilder result = new StringBuilder();
        try (FileLines lines = new FileLines(path())) {
            for (String line : lines) {
            result.append(line + '\n');
            if (charLimit < result.length()) {
                throw new InvalidFileContentException(String.format(
                    "File size limit (%s) exceeded: found more than %s",
                    charLimit, result.length()
                ));
            }
        }
        } catch (UncheckedFileSystemException e) {
            throw new FileSystemException(e.getMessage(), e);
        }
        return result.toString();
    }

    public static long defaultCharLimit() {
        return DEFAULT_CHAR_LIMIT;
    }
}
