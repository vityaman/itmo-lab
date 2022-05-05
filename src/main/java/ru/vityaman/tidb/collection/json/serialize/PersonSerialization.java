package ru.vityaman.tidb.collection.json.serialize;

import ru.vityaman.tidb.collection.data.Person;
import ru.vityaman.tidb.collection.data.exception.InvalidFieldValueException;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.JsonSerialization;
import ru.vityaman.tidb.lang.json.MapJsonObject;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;
import ru.vityaman.tidb.lang.json.exception.JsonExtractionException;

public final class PersonSerialization 
implements JsonSerialization<Person> {
    private static final String HEIGHT = "height";
    private static final String PASSPORT_ID = "passportId";
    private static final String LOCATION = "location";

    private final LocationSerialization location;

    public PersonSerialization(LocationSerialization location) {
        this.location = location;
    }

    @Override
    public JsonObject serialized(Person object) {
        JsonObject result = new MapJsonObject();
        result.put(HEIGHT, object.height());
        result.put(PASSPORT_ID, object.passportId());
        result.put(
            LOCATION, 
            location.serialized(
                object.location()
            )
        );
        return result;
    }

    @Override
    public Person deserialized(JsonObject json) 
    throws JsonDeserializationException {
        if (json.keys().size() != 3) {
            throw new JsonDeserializationException(json, 
                "must have only fields: " + 
                "'height', 'passportId', 'location'"
            );
        }
        try {
            return Person.fresh()
                        .withHeight(json.number(HEIGHT).intValue())
                        .withPassportId(json.string(PASSPORT_ID))
                        .withLocation(
                            location.deserialized(
                                json.object(LOCATION)
                            )
                        )
                        .instance();
        } catch (InvalidFieldValueException | JsonExtractionException e) {
            throw new JsonDeserializationException(json, e);
        }
    }
    
}
