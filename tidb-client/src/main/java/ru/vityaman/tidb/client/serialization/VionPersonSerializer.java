package ru.vityaman.tidb.client.serialization;

import ru.vityaman.tidb.api.data.Person;
import ru.vityaman.vion.MapBasedVionObject;
import ru.vityaman.vion.VionObject;
import ru.vityaman.vion.VionSerializer;
import ru.vityaman.vion.exception.DeserializationException;
import ru.vityaman.vion.exception.FieldExtractionException;

public final class VionPersonSerializer
implements VionSerializer<Person> {
    private static final String HEIGHT = "height";
    private static final String PASSPORT_ID = "passportId";
    private static final String LOCATION = "location";

    private final VionLocationSerializer location;

    public VionPersonSerializer(VionLocationSerializer location) {
        this.location = location;
    }

    @Override
    public VionObject serialize(Person object) {
        VionObject result = new MapBasedVionObject();
        result.put(HEIGHT, object.height());
        result.put(PASSPORT_ID, object.passportId());
        result.put(
            LOCATION, 
            location.serialize(
                object.location()
            )
        );
        return result;
    }

    @Override
    public Person deserialize(VionObject json)
    throws DeserializationException {
        if (json.keys().size() != 3) {
            throw new DeserializationException(json,
                "must have only fields: " + 
                "'height', 'passportId', 'location'"
            );
        }
        try {
            return Person.fresh()
                        .withHeight(json.number(HEIGHT).intValue())
                        .withPassportId(json.string(PASSPORT_ID))
                        .withLocation(
                            location.deserialize(
                                json.object(LOCATION)
                            )
                        )
                        .instance();
        } catch (FieldExtractionException | IllegalArgumentException  e) {
            throw new DeserializationException(json, e);
        }
    }
    
}
