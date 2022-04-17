package ru.vityaman.tidb.data.json;

import java.util.Map;

import ru.vityaman.tidb.data.field.Field;
import ru.vityaman.tidb.data.field.Json;
import ru.vityaman.tidb.data.field.Verified;
import ru.vityaman.tidb.data.model.Coordinates;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.resource.CoordinatesResource;
import ru.vityaman.tidb.data.resource.exception.InvalidResourceException;

/**
 * Json Coordinates.
 */
public final class JsonCoordinates extends JsonResource
                      implements CoordinatesResource, Coordinates {

    private final Field<Number> x;
    private final Field<Number> y;

    public JsonCoordinates(Map<String, Object> json) {
        super(json);
        try {
            this.x = new Verified<>(new Json<>("x", this.json),
                x -> Coordinates.RequireValid.x(x.doubleValue()));
            this.y = new Verified<>(new Json<>("y", this.json),
                y -> Coordinates.RequireValid.y(y.doubleValue()));
        } catch (InvalidValueException | Json.InvalidJsonException e) {
            throw new InvalidResourceException(
                    "Invalid resource " + json + " as " + e.getMessage(), e);
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