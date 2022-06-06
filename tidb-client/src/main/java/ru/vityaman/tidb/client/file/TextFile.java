package ru.vityaman.tidb.client.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import ru.vityaman.tidb.client.file.exception.InvalidFileContentException;


/**
 * File with text content.
 */
public final class TextFile extends AbstractFile<String> {
    private static final long DEFAULT_CHAR_LIMIT = 10000L;

    private final long charLimit;

    /**
     * @param file
     * @param charLimit file can't contain more characters
     */
    public TextFile(Path file, long charLimit) {
        super(file);
        this.charLimit = charLimit;
    }

    public TextFile(Path file) {
        this(file, defaultCharLimit());
    }

    @Override
    public void write(String content) throws IOException {
        try (BufferedOutputStream file =
                new BufferedOutputStream(
                    new FileOutputStream(
                        path().toFile()))
        ) {
            file.write(content.getBytes());
        } catch (IOException e) {
            throw new IOException(String.format(
                "Can't write file as %s",
                e.getMessage()
            ), e);
        }
    }

    @Override
    public String content() throws IOException {
        StringBuilder result = new StringBuilder();
        try (FileLines lines = new FileLines(path())) {
            for (String line : lines) {
                result.append(line).append('\n');
                if (charLimit < result.length()) {
                    throw new InvalidFileContentException(String.format(
                      "File size limit (%s) exceeded: found more than %s",
                        charLimit, result.length()
                    ));
                }
            }
        } catch (UncheckedIOException e) {
            throw new IOException(e.getMessage(), e);
        }
        return result.toString();
    }

    public static long defaultCharLimit() {
        return DEFAULT_CHAR_LIMIT;
    }
}
