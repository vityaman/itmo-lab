package ru.vityaman.tidb.collection.data.valid.validation;

import ru.vityaman.tidb.collection.data.valid.entity.TicketType;
import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;
import ru.vityaman.tidb.collection.transfer.CoordinatesDTO;
import ru.vityaman.tidb.collection.transfer.PersonDTO;
import ru.vityaman.tidb.collection.transfer.TicketDTO;

public final class TicketValidator implements Validator<TicketDTO> {
    private final CoordinatesValidator coordinatesValidator;
    private final PersonValidator personValidator;

    public TicketValidator(
        CoordinatesValidator coordinatesValidator, 
        PersonValidator personValidator
    ) {
        this.coordinatesValidator = coordinatesValidator;
        this.personValidator = personValidator;
    }

    @Override
    public void ensureValid(TicketDTO ticket) throws ValidationException {
        ensureValidShadow(ticket);
        ensureValidCoordinates(ticket.coordinates());
        ensureValidPerson(ticket.person());
    }

    public void ensureValidShadow(TicketDTO ticket) throws ValidationException {
        ensureNotNull("ticket", ticket);    
        ensureValidName(ticket.name());
        ensureValidPrice(ticket.price());
        ensureValidType(ticket.type());
    }

    public void ensureValidName(String name) throws InvalidFieldValueException {
        ensureNotNull("name", name);
        if (name.isEmpty()) {
            throw new InvalidFieldValueException(
                "name",
                "can't be empty"
            );
        }
    }

    public void ensureValidCoordinates(CoordinatesDTO coordinates) 
    throws ValidationException
    {
        coordinatesValidator.ensureValid(coordinates);
    }

    public void ensureValidPrice(Integer price) throws InvalidFieldValueException {
        if (price != null && price <= 0) {
            throw new InvalidFieldValueException(
                "price",
                 "must be positive"
            );
       }
    }  

    public void ensureValidType(TicketType type) throws InvalidFieldValueException {
        ensureNotNull("type", type);
    }

    public void ensureValidPerson(PersonDTO person) throws ValidationException {
        personValidator.ensureValid(person);
    }
}
