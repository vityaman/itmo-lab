package ru.vityaman.tidb.collection.data.valid.validation;

import java.util.Date;

import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;
import ru.vityaman.tidb.collection.transfer.TicketEntryDTO;

public final class TicketEntryValidator implements Validator<TicketEntryDTO> {
    private final TicketValidator ticketValidator;

    public TicketEntryValidator(TicketValidator ticketValidator) {
        this.ticketValidator = ticketValidator;
    }

    @Override
    public void ensureValid(TicketEntryDTO ticketEntry) throws ValidationException {
        ensureValidShadow(ticketEntry);
        ticketValidator.ensureValid(ticketEntry.ticket());    
    }

    public void ensureValidShadow(TicketEntryDTO ticketEntry) 
    throws InvalidFieldValueException 
    {
        ensureNotNull("ticketEntry", ticketEntry);
        ensureValidId(ticketEntry.id());
        ensureValidCreationDate(ticketEntry.creationDate());
    }

    public void ensureValidId(Integer id) throws InvalidFieldValueException {
        ensureNotNull("id", id);
        if (id <= 0) {
            throw new InvalidFieldValueException(
                "id", 
                "must be positive"
            );
        }
    }

    public void ensureValidCreationDate(Date creationDate) 
    throws InvalidFieldValueException 
    {
        ensureNotNull("creationDate", creationDate);
    }
    
}
