package ru.vityaman.tidb.collection.json.serialize;

import ru.vityaman.tidb.collection.data.Coordinates;
import ru.vityaman.tidb.collection.data.exception.InvalidFieldValueException;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.JsonSerialization;
import ru.vityaman.tidb.lang.json.MapJsonObject;
import ru.vityaman.tidb.lang.json.exception.JsonDeserializationException;
import ru.vityaman.tidb.lang.json.exception.JsonExtractionException;

public final class CoordinatesSerialization 
implements JsonSerialization<Coordinates> {
    private static final String X = "x";
    private static final String Y = "y";

    @Override
    public JsonObject serialized(Coordinates object) {
        JsonObject result = new MapJsonObject();
        result.put(X, object.x());
        result.put(Y, object.y());
        return result;
    }

    @Override
    public Coordinates deserialized(JsonObject json) 
    throws JsonDeserializationException {
        if (json.keys().size() != 2) {
            throw new JsonDeserializationException(json, 
                "must have only fields: 'x', 'y'"
            );
        }
        try {
            return Coordinates.fresh()
                        .withX(json.number(X).doubleValue())
                        .withY(json.number(Y).doubleValue())
                        .instance();
        } catch (JsonExtractionException | InvalidFieldValueException e) {
            throw new JsonDeserializationException(json, e);
        }
    }    
}
