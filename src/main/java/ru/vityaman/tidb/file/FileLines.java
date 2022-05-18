package ru.vityaman.tidb.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Iterator;

import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.UncheckedFileSystemException;

/**
 * Returns an iterator of lines in file.
 * Iterator can throw UncheckedFileSystemException.
 */
public final class FileLines implements AutoCloseable, Iterable<String> {
    private final Path path;
    private final BufferedReader file;

    public FileLines(Path path) throws FileSystemException {
        this.path = path;
        try {
            file = new BufferedReader(
                        new InputStreamReader(
                            new FileInputStream(
                                path.toFile())));
        } catch (IOException e) {
            throw new FileSystemException(
                String.format(
                    "Can't read file lines as %s",
                    e.getMessage()
                ),
                e
            );
        }
    }

    @Override
    public void close() throws FileSystemException {
        try {
            file.close();
        } catch (IOException e) {
            throw new FileSystemException(
                String.format(
                    "File system exception: %s",
                    e.getMessage()
                ),
                e
            );
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
                throw UncheckedFileSystemException
                            .whileWorkingWith(path, e);
            }
        }

        @Override
        public String next() {
            return line;
        }
    }
}
