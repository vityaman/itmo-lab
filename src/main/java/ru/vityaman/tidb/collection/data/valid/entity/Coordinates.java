package ru.vityaman.tidb.collection.data.valid.entity;

import ru.vityaman.tidb.collection.data.valid.validation.CoordinatesValidator;
import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;
import ru.vityaman.tidb.collection.transfer.CoordinatesDTO;

public final class Coordinates extends CoordinatesDTO {
    public static final class Factory {
        private final CoordinatesValidator validator;

        public Factory(CoordinatesValidator validator) {
            this.validator = validator;
        }

        public Coordinates newInstanceFrom(CoordinatesDTO coordinates) 
        throws ValidationException 
        {
            validator.ensureValid(coordinates);
            return new Coordinates(coordinates);
        }

        public Another another() {
            return new Another();
        }

        public final class Another {
            private final CoordinatesDTO.Another another;
        
            private Another() {
                another = CoordinatesDTO.another();
            }
        
            public Another withX(Double x) throws InvalidFieldValueException {
                validator.ensureValidX(x);
                another.withX(x);
                return this;
            }
        
            public Another withY(Double y) throws InvalidFieldValueException {
                validator.ensureValidY(y);
                another.withY(y);
                return this;
            }
        
            public Coordinates instance() {
                try {
                    return newInstanceFrom(another.instance());
                } catch (ValidationException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    private Coordinates(CoordinatesDTO validated) {
        super(validated);
    }
}
