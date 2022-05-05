package ru.vityaman.tidb.file;

import java.nio.file.Path;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.InvalidFileContentException;

/**
 * Json file.
 */
public class JsonFile extends AbstractFile<Map<String, Object>> {
    public JsonFile(Path file) {
        // TODO: TextFile charLimit
        super(file);
    }

    public Map<String, Object> content() throws FileSystemException,
                                                InvalidFileContentException {
        try {
            TextFile source = new TextFile(path());
            Map<String, Object> json = new JSONObject(source.content()).toMap();
            return json;
        } catch (JSONException e) {
            throw new InvalidFileContentException(
                String.format(
                    "Invalid json: %s",
                    e.getMessage()
                ),
                e
            );
        }
    }

    @Override
    public void write(Map<String, Object> object) throws FileSystemException {
        String json = new JSONObject(object).toString();
        TextFile destination = new TextFile(path());
        destination.write(json);
    }
}
