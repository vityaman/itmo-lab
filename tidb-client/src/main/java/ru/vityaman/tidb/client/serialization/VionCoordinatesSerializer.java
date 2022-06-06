package ru.vityaman.tidb.client.serialization;

import ru.vityaman.tidb.api.data.Coordinates;
import ru.vityaman.vion.MapBasedVionObject;
import ru.vityaman.vion.VionObject;
import ru.vityaman.vion.VionSerializer;
import ru.vityaman.vion.exception.DeserializationException;
import ru.vityaman.vion.exception.FieldExtractionException;

public final class VionCoordinatesSerializer
implements VionSerializer<Coordinates> {
    private static final String X = "x";
    private static final String Y = "y";

    @Override
    public VionObject serialize(Coordinates object) {
        VionObject result = new MapBasedVionObject();
        result.put(X, object.x());
        result.put(Y, object.y());
        return result;
    }

    @Override
    public Coordinates deserialize(VionObject json)
    throws DeserializationException {
        if (json.keys().size() != 2) {
            throw new DeserializationException(json,
                "must have only fields: 'x', 'y'"
            );
        }
        try {
            return Coordinates.fresh()
                        .withX(json.number(X).doubleValue())
                        .withY(json.number(Y).doubleValue())
                        .instance();
        } catch (FieldExtractionException | IllegalArgumentException e) {
            throw new DeserializationException(json, e);
        }
    }    
}
