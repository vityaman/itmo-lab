package ru.vityaman.tidb.data.file;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import ru.vityaman.tidb.data.file.exception.InvalidFileStructureException;

/**
 * Json file.
 */
public class JsonFile extends AbstractFile<Map<String, Object>> {
    public JsonFile(java.io.File file) {
        super(file);
    }

    public Map<String, Object> content() {
        try {
            return new JSONObject(
                new TextFile(origin()).content()
            ).toMap();
        } catch (JSONException e) {
            throw new InvalidFileStructureException(
                "Invalid json: " + e.getMessage(), e);
        }
    }

    @Override
    public void write(Map<String, Object> json) {
        new TextFile(origin()).write(
            new JSONObject(json).toString()
        );
    }
}
