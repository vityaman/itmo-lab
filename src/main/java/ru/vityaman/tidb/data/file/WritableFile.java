package ru.vityaman.tidb.data.file;

/**
 * wWritable file.
 * @param <T> content type
 */
public interface WritableFile<T> {
    /**
     * @param content to write
     */
    void write(T content);
}
