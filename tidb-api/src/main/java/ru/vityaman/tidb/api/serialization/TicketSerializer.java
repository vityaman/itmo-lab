package ru.vityaman.tidb.api.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.vityaman.tidb.api.data.Ticket;
import ru.vityaman.tidb.api.data.TicketType;

public final class TicketSerializer
implements ObjectSerializer<Ticket> {
    private final CoordinatesSerializer coordinatesSerializer;
    private final PersonSerializer personSerializer;

    public TicketSerializer(
        CoordinatesSerializer coordinatesSerializer,
        PersonSerializer personSerializer
    ) {
        this.coordinatesSerializer = coordinatesSerializer;
        this.personSerializer = personSerializer;
    }

    @Override
    public Ticket read(ObjectInputStream stream) 
    throws IOException, DeserializationException 
    {
        return ObjectSerializer.deserialized(
            () -> Ticket.fresh()
                        .withName((String) stream.readObject())
                        .withCoordinates(coordinatesSerializer.read(stream))
                        .withPrice((Integer) stream.readObject())
                        .withType((TicketType) stream.readObject())
                        .withPerson(personSerializer.read(stream))
                        .instance()
        );
    }

    @Override
    public void write(ObjectOutputStream stream, Ticket object) 
    throws IOException 
    {
        stream.writeObject(object.name());
        coordinatesSerializer.write(stream, object.coordinates());
        stream.writeObject(object.price().orElse(null));
        stream.writeObject(object.type());
        personSerializer.write(stream, object.person());
    }

    public CoordinatesSerializer coordinatesSerializer() {
        return coordinatesSerializer;
    }    

    public PersonSerializer personSerializer() {
        return personSerializer;
    }
}
