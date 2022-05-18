package ru.vityaman.tidb.collection.data.valid.entity;

import ru.vityaman.tidb.collection.data.valid.validation.PersonValidator;
import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;
import ru.vityaman.tidb.collection.transfer.PersonDTO;

public final class Person extends PersonDTO {

    @Override
    public Location location() {
        return (Location) super.location();
    }

    public static final class Factory {
        private final Location.Factory locationFactory;
        private final PersonValidator validator;

        public Factory(
            Location.Factory locationFactory, 
            PersonValidator validator
        ) {
            this.locationFactory = locationFactory;
            this.validator = validator;
        }

        public Person newInstanceFrom(PersonDTO person) 
        throws ValidationException 
        {
            validator.ensureValidShadow(person);
            return new Person(
                PersonDTO.another(person)
                    .withLocation(
                        locationFactory.newInstanceFrom(
                            person.location()
                        )
                    )
                    .instance()
            );
        }

        public Another another() {
            return new Another();
        }

        public final class Another {
            private final PersonDTO.Another builder;

            private Another() {
                builder = PersonDTO.another();
            }

            public Another withHeight(Integer height) 
            throws InvalidFieldValueException 
            {
                validator.ensureValidHeight(height);
                builder.withHeight(height);
                return this;
            }

            public Another withPassportId(String passportId) 
            throws InvalidFieldValueException 
            {
                validator.ensureValidPassportId(passportId);    
                builder.withPassportId(passportId);
                return this;
            }

            public Another withLocation(Location location) {
                validator.ensureNotNull("location", location);
                builder.withLocation(location);
                return this;
            }

            public Person instance() {
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

    private Person(PersonDTO other) {
        super(other);
    }
}
