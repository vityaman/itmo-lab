package ru.vityaman.tidb.api.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.vityaman.tidb.api.data.Person;

public final class PersonSerializer
implements ObjectSerializer<Person> {
    private final LocationSerializer locationSerializer;

    public PersonSerializer(LocationSerializer locationSerializer) {
        this.locationSerializer = locationSerializer;
    }

    @Override
    public Person read(ObjectInputStream stream) 
    throws IOException, DeserializationException 
    {
        return ObjectSerializer.deserialized(
            () -> Person.fresh()
                        .withHeight(stream.readInt())
                        .withPassportId((String) stream.readObject())
                        .withLocation(locationSerializer.read(stream))
                        .instance()
        );
    }

    @Override
    public void write(ObjectOutputStream stream, Person object) 
    throws IOException 
    {
        stream.writeInt(object.height());
        stream.writeObject(object.passportId());
        locationSerializer.write(stream, object.location());
    }

    public LocationSerializer locationSerializator() {
        return locationSerializer;
    }    
}
