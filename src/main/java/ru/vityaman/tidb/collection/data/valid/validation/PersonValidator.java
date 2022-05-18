package ru.vityaman.tidb.collection.data.valid.validation;

import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;
import ru.vityaman.tidb.collection.transfer.LocationDTO;
import ru.vityaman.tidb.collection.transfer.PersonDTO;

public final class PersonValidator implements Validator<PersonDTO> {
    private final LocationValidator locationValidator;

    public PersonValidator(LocationValidator locationValidator) {
        this.locationValidator = locationValidator;
    }

    @Override
    public void ensureValid(PersonDTO person) throws ValidationException {
        ensureValidShadow(person);
        ensureValidLocation(person.location());
    }

    public void ensureValidShadow(PersonDTO person) throws InvalidFieldValueException {
        ensureNotNull("person", person);        
        ensureValidHeight(person.height());
        ensureValidPassportId(person.passportId());
    }

    public void ensureValidHeight(Integer height) throws InvalidFieldValueException {
        ensureNotNull("height", height);
        if (height <= 0) {
            throw new InvalidFieldValueException(
                "height",
                "must be positive"
            );
        }
    }

    public void ensureValidPassportId(String passportId) 
    throws InvalidFieldValueException 
    {
        ensureNotNull("passportId", passportId);
        if (passportId.length() < 5 || 47 < passportId.length()) {
            throw new InvalidFieldValueException(
                "passportId",
                "must be in [5, 47]"
            );
        }
    }

    public void ensureValidLocation(LocationDTO location) 
    throws InvalidFieldValueException 
    {
        locationValidator.ensureValid(location);
    }    
    
}
