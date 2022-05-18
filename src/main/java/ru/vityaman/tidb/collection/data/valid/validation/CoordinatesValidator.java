package ru.vityaman.tidb.collection.data.valid.validation;

import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.transfer.CoordinatesDTO;

public final class CoordinatesValidator implements Validator<CoordinatesDTO> 
{
    public void ensureValid(CoordinatesDTO coordinates) 
    throws InvalidFieldValueException 
    {
        ensureNotNull("coordinates", coordinates);
        ensureValidX(coordinates.x());
        ensureValidY(coordinates.y());
    }

    public void ensureValidX(Double x) throws InvalidFieldValueException {
        ensureNotNull("x", x);
    }

    public void ensureValidY(Double y) throws InvalidFieldValueException {
        ensureNotNull("y", y);
        if (762 < y) {
            throw new InvalidFieldValueException(
                "y", 
                "max value is 762"
            );
        }
    } 
}
