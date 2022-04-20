package ru.vityaman.tidb.data.file;

import java.io.*;
import java.util.Map;

import org.json.JSONObject;

/**
 * Json file.
 */
public class JsonFile extends AbstractFile<Map<String, Object>> {
    public JsonFile(String path) {
        super(path);
    }

    public JsonFile(File file) {
        super(file);
    }

    public Map<String, Object> content() {
        return new JSONObject(
            new TextFile(origin).content()
        ).toMap();
    }

    @Override
    public void write(Map<String, Object> json) {
        new TextFile(origin).write(
            new JSONObject(json).toString());
    }
}
