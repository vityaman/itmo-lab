package ru.vityaman.tidb.api.data;


public final class Person {
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

    @Override
    public String toString() {
        return String.format(
            "Person [passportId: %s, height: %s, location: %s]",
            passportId, height, location
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Person that = (Person) other;
        return height == that.height
            && passportId.equals(that.passportId)
            && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return passportId.hashCode() + 
            31 * (height + 
            31 * location.hashCode());
    }

    public static Builder fresh() {
        return new Builder();
    }

    public static Builder updated(Person person) {
        return new Builder(person);
    }

    public static final class Builder {
        private Integer height;
        private String passportId;
        private Location location;

        private Builder() {}

        private Builder(Person person) {
            height = person.height;
            passportId = person.passportId;
            location = person.location;
        }

        public Builder withHeight(int height) {
            if (height <= 0) {
                throw new IllegalArgumentException(
                    "height must be positive"
                );
            }
            this.height = height;
            return this;
        }

        public Builder withPassportId(String passportId) {
            if (passportId.length() < 5 || 47 < passportId.length()) {
                throw new IllegalArgumentException(
                    "passportId.length must be in [5, 47]"
                );
            }
            this.passportId = passportId;
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        // TODO: nullnullnullnulll
        public Person instance() {
            if (height == null || passportId == null || location == null) {
                throw new IllegalStateException(
                    String.format(
                        "height: %s, passportId: %s, location: %s",
                        height, passportId, location
                ));
            }
            return new Person(this);
        }
    }

    private Person(Builder builder) {
        height = builder.height;
        passportId = builder.passportId;
        location = builder.location;
    }
}
