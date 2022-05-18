package ru.vityaman.tidb.collection.data.valid.validation;

import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.transfer.LocationDTO;

public final class LocationValidator implements Validator<LocationDTO> {
    public void ensureValid(LocationDTO location) throws InvalidFieldValueException {
        ensureNotNull("location", location);
        ensureValidName(location.name());
        ensureValidX(location.x());
        ensureValidY(location.y());
        ensureValidZ(location.z());
    }

    public void ensureValidName(String name) throws InvalidFieldValueException {
        ensureNotNull("name", name);
    }

    public void ensureValidX(Float x) throws InvalidFieldValueException {
        ensureNotNull("x", x);
    }

    public void ensureValidY(Double y) throws InvalidFieldValueException {
        ensureNotNull("y", y);
    } 

    public void ensureValidZ(Float z) throws InvalidFieldValueException {
        ensureNotNull("z", z);
    } 
}
