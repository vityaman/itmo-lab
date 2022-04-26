package ru.vityaman.tidb.data.json;

import java.util.Map;

import ru.vityaman.tidb.data.resource.PersonResource;
import ru.vityaman.tidb.data.resource.exception.InvalidResourceException;
import ru.vityaman.tidb.data.json.exception.InvalidJsonResourceException;
import ru.vityaman.tidb.data.json.field.Field;
import ru.vityaman.tidb.data.json.field.JsonField;
import ru.vityaman.tidb.data.json.field.Verified;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;

/**
 * Json Person.
 */
public final class JsonPerson extends JsonResource
                              implements PersonResource {

    private final Field<Integer> height;
    private final Field<String> passportId;
    private final JsonLocation location;

    public JsonPerson(Map<String, Object> json) throws InvalidResourceException {
        super(json);
        try {
            this.height = new Verified<>(
                new JsonField<>(this.json, "height"),
                RequireValid::height
            );
            this.passportId = new Verified<>(
                new JsonField<>(this.json, "passportId"),
                RequireValid::passportId
            );
            this.location = new JsonLocation(
                new JsonField<Map<String, Object>>(this.json,
                "location").value()
            );
        } catch (InvalidJsonResourceException
                | InvalidValueException
                | JsonField.InvalidJsonException e) {
                throw new InvalidJsonResourceException(json, e);
        }
    }

    @Override
    public int height() {
        return this.height.value();
    }

    @Override
    public void setHeight(int height) {
        this.height.set(height);
    }

    @Override
    public String passportId() {
        return this.passportId.value();
    }

    @Override
    public void setPassportId(String passportId) {
        this.passportId.set(passportId);
    }

    @Override
    public JsonLocation location() {
        return location;
    }
}
