package ru.vityaman.tidb.data.file;

import java.io.*;
import java.util.Map;

import org.json.JSONObject;
import ru.vityaman.tidb.data.file.exception.FileAccessException;
import ru.vityaman.tidb.data.file.exception.NoSuchFileException;

/**
 * Json file.
 */
public class JsonFile extends AbstractFile
                            implements ReadableFile<Map<String, Object>>,
                                       WritableFile<Map<String, Object>> {
    public JsonFile(String path) {
        super(path);
    }

    public JsonFile(File file) {
        super(file);
    }

    public Map<String, Object> content() {
        return new JSONObject(
            new TextReadFile(origin).content()
        ).toMap();
    }

    // TODO: TextWriteFile
    @Override
    public void write(Map<String, Object> json) {
        String content = new JSONObject(json).toString();
        try (BufferedOutputStream file =
                new BufferedOutputStream(
                    new FileOutputStream(origin))) {
            file.write(content.getBytes());
        } catch (FileNotFoundException e) {
            throw new NoSuchFileException(String.format(
                "File %s does not exist", origin.getName()),e);
        } catch (IOException e) {
            throw new FileAccessException(
                    "Can't write file as " + e.getMessage(), e);
        }
    }
}
