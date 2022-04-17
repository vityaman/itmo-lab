package ru.vityaman.tidb.data.file;

import ru.vityaman.tidb.data.file.exception.FileAccessException;

import java.io.*;

public final class TextReadFile extends AbstractFile
                            implements ReadableFile<String> {

    public TextReadFile(String path) {
        super(path);
    }

    public TextReadFile(File file) {
        super(file);
    }

    public String content() {
        StringBuilder content = new StringBuilder();

        try (
            BufferedReader file = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(origin)))
        ) {
            String line;
            while ((line = file.readLine()) != null) {
                content.append(line + '\n');
            }
        } catch (IOException e) {
            throw new FileAccessException(
                    "Can't open file: " + e.getMessage(), e);
        }

        return content.toString();
    }
}
