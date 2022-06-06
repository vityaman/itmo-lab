package ru.vityaman.tidb.client.serialization;

import ru.vityaman.tidb.api.data.Location;
import ru.vityaman.vion.MapBasedVionObject;
import ru.vityaman.vion.VionObject;
import ru.vityaman.vion.VionSerializer;
import ru.vityaman.vion.exception.DeserializationException;
import ru.vityaman.vion.exception.FieldExtractionException;

public final class VionLocationSerializer
implements VionSerializer<Location> {
    private static final String NAME = "name";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    
    @Override
    public VionObject serialize(Location object) {
        VionObject result = new MapBasedVionObject();
        result.put(NAME, object.name());
        result.put(X, object.x());
        result.put(Y, object.y());
        result.put(Z, object.z());
        return result;
    }

    @Override
    public Location deserialize(VionObject json)
    throws DeserializationException {
        if (json.keys().size() != 4) {
            throw new DeserializationException(json,
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
        } catch (FieldExtractionException e) {
            throw new DeserializationException(json, e);
        }
    }
}
