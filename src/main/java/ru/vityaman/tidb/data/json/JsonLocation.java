package ru.vityaman.tidb.data.json;

import java.util.Map;

import ru.vityaman.tidb.data.json.exception.InvalidJsonResourceException;
import ru.vityaman.tidb.data.json.field.JsonField;
import ru.vityaman.tidb.data.json.field.Verified;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.data.resource.LocationResource;
import ru.vityaman.tidb.data.resource.exception.InvalidResourceException;

/**
 * Json Location.
 */
public final class JsonLocation extends JsonResource
                                implements LocationResource {

    private final Verified<Number> x;
    private final Verified<Number> y;
    private final Verified<Number> z;
    private final Verified<String> name;

    public JsonLocation(Map<String, Object> json)
                                        throws InvalidResourceException {
        super(json);
        try {
            this.x = new Verified<>(
                new JsonField<>(this.json, "x"),
                x -> Location.RequireValid.x(x.floatValue())
            );
            this.y = new Verified<>(
                new JsonField<>(this.json, "y"),
                y -> Location.RequireValid.y(y.doubleValue())
            );
            this.z = new Verified<>(
                new JsonField<>(this.json, "z"),
                z -> Location.RequireValid.z(z.floatValue())
            );
            this.name = new Verified<>(
                new JsonField<>(this.json, "name"),
                RequireValid::name);
        } catch (InvalidValueException | JsonField.InvalidJsonException e) {
            throw new InvalidJsonResourceException(json, e);
        }
    }

    @Override
    public float x() {
        return x.value().floatValue();
    }

    @Override
    public double y() {
        return y.value().doubleValue();
    }

    @Override
    public float z() {
        return z.value().floatValue();
    }

    @Override
    public String name() {
        return name.value();
    }

    @Override
    public void setX(float x) {
        this.x.set(x);
    }

    @Override
    public void setY(double y) {
        this.y.set(y);
    }

    @Override
    public void setZ(float z) {
        this.z.set(z);
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }
}
