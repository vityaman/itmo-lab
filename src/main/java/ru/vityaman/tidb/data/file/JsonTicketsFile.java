package ru.vityaman.tidb.data.file;

import java.util.Map;

import ru.vityaman.tidb.data.json.JsonTickets;

public final class JsonTicketsFile implements File<JsonTickets> {
    private final File<Map<String, Object>> origin;

    public JsonTicketsFile(java.io.File origin) {
        this.origin = new CreateOnWriteFile<>(
            new JsonFile(origin),
            JsonTickets.defaultJson()
        );
    }

    @Override
    public void write(JsonTickets content) {
        origin.write(content.json());
    }

    @Override
    public JsonTickets content() {
        return new JsonTickets(origin.content());
    }

    @Override
    public java.io.File origin() {
        return origin.origin();
    }
}
