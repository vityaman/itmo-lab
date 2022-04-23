package ru.vityaman.tidb.data.dto;

import java.util.Objects;

import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.data.model.Person;

/**
 * Person Bean.
 */
public final class PersonInfo implements Person {
    private final int height;
    private final String passportId;
    private final Location location;

    public int height() {
        return height;
    }

    public String passportId() {
        return passportId;
    }

    public Location location() {
        return location;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer height;
        private String passportId;
        private Location location;

        Builder() {}

        public Builder withHeight(int height) {
            this.height = Person.RequireValid.height(height);
            return this;
        }

        public Builder withPassportId(String passportId) {
            this.passportId = Person.RequireValid.passportId(passportId);
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public PersonInfo instance() {
            return new PersonInfo(this);
        }
    }

    private PersonInfo(Builder builder) {
        height = Objects.requireNonNull(builder.height);
        passportId = Objects.requireNonNull(builder.passportId);
        location = Objects.requireNonNull(builder.location);
    }
}
