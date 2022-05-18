package ru.vityaman.tidb.collection.data.valid.entity;

import ru.vityaman.tidb.collection.data.valid.validation.TicketEntryValidator;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;
import ru.vityaman.tidb.collection.transfer.TicketEntryDTO;

public final class TicketEntry extends TicketEntryDTO {

    @Override
    public Ticket ticket() {
        return (Ticket) super.ticket();
    }

    public TicketEntry updated(Ticket ticket) {
        return new TicketEntry(
            new TicketEntryDTO(
                id(), 
                creationDate(),
                ticket
            )
        );
    }

    public static final class Factory {
        private final Ticket.Factory ticketFactory;
        private final TicketEntryValidator validator;

        public Factory(
            Ticket.Factory ticketFactory,
            TicketEntryValidator validator
        ) {
            this.ticketFactory = ticketFactory;
            this.validator = validator;
        }

        public TicketEntry newInstanceFrom(TicketEntryDTO ticketEntry) 
        throws ValidationException 
        {
            validator.ensureValidShadow(ticketEntry);
            return new TicketEntry(
                new TicketEntryDTO(
                    ticketEntry.id(), 
                    ticketEntry.creationDate(), 
                    ticketFactory.newInstanceFrom(ticketEntry.ticket())
                )
            );
        }
    }

    private TicketEntry(TicketEntryDTO other) {
        super(other);
    }
}
