package ru.vityaman.tidb.data.json;

import java.util.Map;

import ru.vityaman.tidb.data.field.Field;
import ru.vityaman.tidb.data.model.Person;
import ru.vityaman.tidb.data.resource.PersonResource;
import ru.vityaman.tidb.data.field.Verified;
import ru.vityaman.tidb.data.json.field.JsonField;

/**
 * Json Person.
 */
public final class JsonPerson extends JsonResource
                              implements PersonResource, Person {

    private final Field<Integer> height;
    private final Field<String> passportId;
    private final JsonLocation location;

    public JsonPerson(Map<String, Object> json) {
        super(json);

        this.height = new Verified<>(new JsonField<>("height", this.json),
                RequireValid::height);
        this.passportId = new Verified<>(new JsonField<>("passportId", this.json),
                RequireValid::passportId);
        this.location = new JsonLocation(new JsonField<Map<String, Object>>(
            "location", this.json).value());
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
