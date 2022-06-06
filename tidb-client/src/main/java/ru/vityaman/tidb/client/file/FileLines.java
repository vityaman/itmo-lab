package ru.vityaman.tidb.client.file;

import java.io.*;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Returns an iterator of lines in file.
 * Iterator can throw UncheckedFileSystemException.
 */
public final class FileLines implements AutoCloseable, Iterable<String> {
    private final Path path;
    private final BufferedReader file;

    public FileLines(Path path) throws IOException {
        this.path = path;
        try {
            file = new BufferedReader(
                        new InputStreamReader(
                            new FileInputStream(
                                path.toFile())));
        } catch (IOException e) {
            throw new IOException(String.format(
                "Can't read file lines as %s",
                e.getMessage()), e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            file.close();
        } catch (IOException e) {
            throw new IOException(String.format(
                "File system exception: %s",
                e.getMessage()
            ), e);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new FileLinesIterator();
    }
    private final class FileLinesIterator implements Iterator<String> {
        private String line;

        @Override
        public boolean hasNext() {
            try {
                line = file.readLine();
                return line != null;
            } catch (IOException e) {
                throw new UncheckedIOException(String.format(
                    "Error while working with file %s: %s",
                    path, e.getMessage()
                ), e);
            }
        }

        @Override
        public String next() {
            return line;
        }
    }
}
