package ru.vityaman.tidb.data.file;

import java.nio.file.Path;

import ru.vityaman.tidb.data.file.exception.FileSystemException;
import ru.vityaman.tidb.data.file.exception.InvalidFileContentException;

public interface File<T> {
    void write(T content) throws FileSystemException;
    T content() throws FileSystemException, InvalidFileContentException;
    Path path();
}
