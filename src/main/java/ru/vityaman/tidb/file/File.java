package ru.vityaman.tidb.file;

import java.nio.file.Path;

import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.InvalidFileContentException;

public interface File<T> {
    void write(T content) throws FileSystemException;
    T content() throws FileSystemException, InvalidFileContentException;
    Path path();
}
