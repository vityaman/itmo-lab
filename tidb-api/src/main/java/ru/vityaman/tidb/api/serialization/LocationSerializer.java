package ru.vityaman.tidb.api.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.vityaman.tidb.api.data.Location;

public final class LocationSerializer
implements ObjectSerializer<Location> {
    @Override
    public Location read(ObjectInputStream stream) 
    throws IOException, DeserializationException 
    {
        return ObjectSerializer.deserialized(
            () -> Location.fresh()
                        .withX(stream.readFloat())
                        .withY(stream.readDouble())
                        .withZ(stream.readFloat())
                        .withName((String) stream.readObject())
                        .instance()
        );
    }

    @Override
    public void write(ObjectOutputStream stream, Location object) 
    throws IOException 
    {
        stream.writeFloat(object.x());
        stream.writeDouble(object.y());
        stream.writeFloat(object.z());
        stream.writeObject(object.name());
    }
}
