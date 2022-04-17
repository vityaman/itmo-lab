package ru.vityaman.tidb.data.bean;

import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.data.model.Person;

/**
 * Person Bean.
 */
public final class PersonBean {
    private int height;
    private String passportId;
    private Location location;

    public int height() {
        return height;
    }

    public void setHeight(int height) {
        this.height = Person.RequireValid.height(height);
    }

    public String passportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = Person.RequireValid.passportId(passportId);
    }

    public Location location() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return validated person
     */
    public Person validated() {
        Person.RequireValid.height(height);
        Person.RequireValid.passportId(passportId);
        return new Person() {
            @Override public int height()
            { return height; }
            @Override public String passportId()
            { return passportId; }
            @Override public Location location()
            { return location; }
        };
    }
}
