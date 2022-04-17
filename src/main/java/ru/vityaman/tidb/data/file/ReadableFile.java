package ru.vityaman.tidb.data.file;

/**
 * Readable File.
 * @param <T> content type
 */
public interface ReadableFile<T> {
    /**
     * @return file content
     */
    T content();
}
