package ru.vityaman.tidb.collection.data.valid.entity;

import ru.vityaman.tidb.collection.data.valid.validation.LocationValidator;
import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;
import ru.vityaman.tidb.collection.transfer.LocationDTO;

public final class Location extends LocationDTO {
    public static final class Factory {
        private final LocationValidator validator;

        public Factory(LocationValidator validator) {
            this.validator = validator;
        }

        public Location newInstanceFrom(LocationDTO location) 
        throws ValidationException 
        {
            validator.ensureValid(location);
            return new Location(location);
        }

        public Another another() {
            return new Another();
        }

        public final class Another {
            private final LocationDTO.Another builder;
    
            private Another() {
                builder = LocationDTO.another();
            }
    
            public Another withName(String name) throws InvalidFieldValueException {
                validator.ensureValidName(name);
                builder.withName(name);
                return this;
            }

            public Another withX(Float x) throws InvalidFieldValueException {
                validator.ensureValidX(x);
                builder.withX(x);
                return this;
            }
    
            public Another withY(Double y) throws InvalidFieldValueException {
                validator.ensureValidY(y);
                builder.withY(y);
                return this;
            }
    
            public Another withZ(Float z) throws InvalidFieldValueException {
                validator.ensureValidZ(z);
                builder.withZ(z);
                return this;
            }
    
            public Location instance() {
                try {
                    return newInstanceFrom(
                        builder.instance()
                    );
                } catch (ValidationException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    private Location(LocationDTO other) {
        super(other);
    }
}
