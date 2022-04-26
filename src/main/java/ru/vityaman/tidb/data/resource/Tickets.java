package ru.vityaman.tidb.data.resource;

import java.util.Collection;

import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.resource.exception.ResourceNotFoundException;

public interface Tickets {
    TicketResource ticketWithId(int id) throws ResourceNotFoundException;
    Collection<TicketResource> all();
    Insertion insertionOf(Ticket ticket);
    void removeWithById(int id) throws ResourceNotFoundException;

    interface Insertion {
        TicketResource result();
        Insertion execute();
    }
}
