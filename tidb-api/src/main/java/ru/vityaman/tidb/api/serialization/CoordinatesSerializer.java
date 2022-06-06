package ru.vityaman.tidb.api.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.vityaman.tidb.api.data.Coordinates;

public final class CoordinatesSerializer
implements ObjectSerializer<Coordinates> {
    @Override
    public Coordinates read(ObjectInputStream stream) 
    throws IOException, DeserializationException 
    {
        return ObjectSerializer.deserialized(
            () -> Coordinates.fresh()
                    .withX(stream.readDouble())
                    .withY(stream.readDouble())
                    .instance()
        );
    }

    @Override
    public void write(ObjectOutputStream stream, Coordinates object) 
    throws IOException {
        stream.writeDouble(object.x());
        stream.writeDouble(object.y());
    }    
}
