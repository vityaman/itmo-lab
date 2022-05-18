package ru.vityaman.tidb.collection.transfer;

public class PersonDTO {
    private Integer height;
    private String passportId;
    private LocationDTO location;

    public PersonDTO(int height, String passportId, LocationDTO location) {
        this.height = height;
        this.passportId = passportId;
        this.location = location;
    }

    public final Integer height() {
        return height;
    }

    public final String passportId() {
        return passportId;
    }

    public LocationDTO location() {
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
        if (other == null || getClass() != other.getClass()) return false;
        PersonDTO that = (PersonDTO) other;
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

    public static Another another() {
        return new Another(new PersonDTO());
    }

    public static Another another(PersonDTO other) {
        return new Another(other);
    }

    public static final class Another {
        private final PersonDTO result;

        private Another(PersonDTO init) {
            result = new PersonDTO(init);
        }

        public Another withHeight(Integer height) {
            result.height = height;
            return this;
        }

        public Another withPassportId(String passportId) {
            result.passportId = passportId;
            return this;
        }

        public Another withLocation(LocationDTO location) {
            result.location = location;
            return this;
        }

        public PersonDTO instance() {
            return result;
        }
    }
    
    private PersonDTO() {}

    protected PersonDTO(PersonDTO other) {
        this(
            other.height, 
            other.passportId, 
            other.location
        );
    }
}
