package ru.vityaman.tidb.client.file;

import java.io.IOException;
import java.nio.file.Path;


/**
 * Represents a writable and readable file.
 */
public interface File<T> {
    void write(T content) throws IOException;
    T content() throws IOException;
    Path path();
}
