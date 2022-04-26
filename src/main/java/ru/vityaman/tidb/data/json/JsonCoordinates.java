package ru.vityaman.tidb.data.json;

import java.util.Map;

import ru.vityaman.tidb.data.json.exception.InvalidJsonResourceException;
import ru.vityaman.tidb.data.json.field.Field;
import ru.vityaman.tidb.data.json.field.JsonField;
import ru.vityaman.tidb.data.json.field.Verified;
import ru.vityaman.tidb.data.model.Coordinates;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.resource.CoordinatesResource;
import ru.vityaman.tidb.data.resource.exception.InvalidResourceException;

/**
 * Json Coordinates.
 */
public final class JsonCoordinates extends JsonResource
                      implements CoordinatesResource {

    private final Field<Number> x;
    private final Field<Number> y;

    public JsonCoordinates(Map<String, Object> json)
                                                throws InvalidResourceException {
        super(json);
        try {
            this.x = new Verified<>(
                new JsonField<>(this.json, "x"),
                x -> Coordinates.RequireValid.x(x.doubleValue())
            );
            this.y = new Verified<>(
                new JsonField<>(this.json, "y"),
                y -> Coordinates.RequireValid.y(y.doubleValue())
            );
        } catch (InvalidValueException | JsonField.InvalidJsonException e) {
            throw new InvalidJsonResourceException(json, e);
        }
    }

    @Override
    public double x() {
        return x.value().doubleValue();
    }

    @Override
    public double y() {
        return y.value().doubleValue();
    }

    @Override
    public void setX(double x) {
        this.x.set(x);
    }

    @Override
    public void setY(double y) {
        this.y.set(y);
    }
}