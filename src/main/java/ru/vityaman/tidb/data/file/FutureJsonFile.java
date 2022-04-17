package ru.vityaman.tidb.data.file;

import ru.vityaman.tidb.data.file.exception.FileAccessException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Json file that can be absent,
 * but will be created on writing content.
 */
public class FutureJsonFile extends JsonFile {
    private final Map<String, Object> content;

    /**
     * @param path to file
     * @param content default if file absent
     */
    public FutureJsonFile(String path, Map<String, Object> content) {
        super(path);
        this.content = new HashMap<>(content);
    }

    public FutureJsonFile(File file, Map<String, Object> content) {
        super(file);
        this.content = new HashMap<>(content);
    }

    @Override
    public Map<String, Object> content() {
        if (!exists()) {
            return content;
        }
        return super.content();
    }

    @Override
    public void write(Map<String, Object> json) {
        if (!exists()) {
            try { origin.createNewFile(); } catch (IOException e) {
                throw new FileAccessException(String.format(
                        "Can't create new file %s", origin.getName()), e);
            }
        }
        super.write(json);
    }

    private boolean exists() {
        return origin.exists();
    }
}
