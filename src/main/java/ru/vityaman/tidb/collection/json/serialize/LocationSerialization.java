package ru.vityaman.tidb.collection.json.serialize;

import ru.vityaman.tidb.collection.data.Location;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.JsonSerialization;
import ru.vityaman.tidb.lang.json.MapJsonObject;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;
import ru.vityaman.tidb.lang.json.exception.JsonExtractionException;

public final class LocationSerialization 
implements JsonSerialization<Location> {
    private static final String NAME = "name";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    
    @Override
    public JsonObject serialized(Location object) {
        JsonObject result = new MapJsonObject();
        result.put(NAME, object.name());
        result.put(X, object.x());
        result.put(Y, object.y());
        result.put(Z, object.z());
        return result;
    }

    @Override
    public Location deserialized(JsonObject json) 
    throws JsonDeserializationException {
        if (json.keys().size() != 4) {
            throw new JsonDeserializationException(json, 
                "must have only fields: 'name', 'x', 'y', 'z'"
            );
        }
        try {
            return Location.fresh()
                        .withName(json.string(NAME))
                        .withX(json.number(X).floatValue())
                        .withY(json.number(Y).doubleValue())
                        .withZ(json.number(Z).floatValue())
                        .instance();
        } catch (JsonExtractionException e) {
            throw new JsonDeserializationException(json, e);
        }
    }
}
