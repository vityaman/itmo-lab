package ru.vityaman.tidb.data.resource;

import java.util.Collection;

import ru.vityaman.tidb.data.model.Ticket;

public interface Tickets {
    TicketResource ticketWithId(int id);
    Collection<TicketResource> all();
    Insertion insertionOf(Ticket ticket);
    void removeWithById(int id);

    interface Insertion {
        TicketResource result();
        Insertion execute();
    }
}
