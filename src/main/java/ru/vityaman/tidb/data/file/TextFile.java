package ru.vityaman.tidb.data.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.vityaman.tidb.data.file.exception.FileAccessException;
import ru.vityaman.tidb.data.file.exception.NoSuchFileException;

public final class TextFile extends AbstractFile<String> {
    public TextFile(java.io.File file) {
        super(file);
    }

    @Override
    public void write(String content) {
        try (BufferedOutputStream file =
                new BufferedOutputStream(
                    new FileOutputStream(origin()))
        ) {
            file.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new NoSuchFileException(String.format(
                "File %s does not exist", origin().getName()),e);
        } catch (IOException e) {
            throw new FileAccessException(
                    "Can't write file as " + e.getMessage(), e);
        }
    }

    @Override
    public String content() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader file = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(origin())))
        ) {
            String line;
            while ((line = file.readLine()) != null) {
                result.append(line + '\n');
            }
        } catch (IOException e) {
            throw new FileAccessException(
                    "Can't open file: " + e.getMessage(), e);
        }
        return result.toString();
    }
}
