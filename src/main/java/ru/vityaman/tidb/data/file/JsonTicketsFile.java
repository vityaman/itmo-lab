package ru.vityaman.tidb.data.file;

import java.nio.file.Path;
import java.util.Map;

import ru.vityaman.tidb.data.file.exception.FileSystemException;
import ru.vityaman.tidb.data.file.exception.InvalidFileContentException;
import ru.vityaman.tidb.data.json.JsonTickets;
import ru.vityaman.tidb.data.json.exception.InvalidJsonResourceException;
import ru.vityaman.tidb.data.resource.exception.InvalidResourceException;

public final class JsonTicketsFile implements File<JsonTickets> {
    private final File<Map<String, Object>> origin;

    public JsonTicketsFile(Path path) {
        origin = new CreateOnWriteFile<>(
            new JsonFile(path),
            JsonTickets.defaultJson()
        );
    }

    @Override
    public void write(JsonTickets content) throws FileSystemException {
        origin.write(content.json());
    }

    @Override
    public JsonTickets content() throws FileSystemException,
                                        InvalidFileContentException {
        try {
            return new JsonTickets(origin.content());
        } catch (InvalidJsonResourceException | InvalidResourceException e) {
            throw new InvalidFileContentException(
                String.format(
                    "Invalid file structure: %s",
                    e.getMessage()
                ),
                e
            );
        }
    }

    @Override
    public Path path() {
        return origin.path();
    }
}
