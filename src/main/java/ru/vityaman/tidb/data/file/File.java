package ru.vityaman.tidb.data.file;

public interface File<T> {
    void write(T content);
    T content();
    java.io.File origin();
}
