package ru.vityaman.tidb.file;

import java.nio.file.Path;
import java.util.Map;

import ru.vityaman.tidb.collection.data.TicketDataset;
import ru.vityaman.tidb.collection.json.serialize.DatasetSerialization;
import ru.vityaman.tidb.file.exception.FileSystemException;
import ru.vityaman.tidb.file.exception.InvalidFileContentException;
import ru.vityaman.tidb.lang.json.MapJsonObject;
import ru.vityaman.tidb.lang.json.exception.JsonConversionException;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;

/**
 * File contains TicketDataset.
 */
public final class TicketDatasetFile implements File<TicketDataset> {
    private final JsonFile origin;
    private final DatasetSerialization serialization;

    public TicketDatasetFile(
        JsonFile origin, 
        DatasetSerialization serialization
    ) {
        this.origin = origin;
        this.serialization = serialization;
    }

    @Override
    public void write(TicketDataset content) throws FileSystemException {
        origin.write(
            serialization.serialized(
                content
            ).toMap()
        );
    }

    @Override
    public TicketDataset content() 
    throws FileSystemException, InvalidFileContentException {
        Map<String, Object> content = origin.content();
        try {
            return serialization.deserialized(
                MapJsonObject.fromMap(
                    content
                )
            );
        } catch (JsonDeserializationException
                | JsonConversionException e) {
            throw new InvalidFileContentException(e);
        }
    }

    @Override
    public Path path() {
        return origin.path();
    }
}
