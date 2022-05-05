package ru.vityaman.tidb.collection.data;

import java.util.Date;

public final class TicketEntry extends Ticket {
    private final int id;
    private final Date creationDate;

    public TicketEntry(int id, Date creationDate, Ticket ticket) {
        super(ticket);
        this.id = id;
        this.creationDate = creationDate;
    }

    public final int id() {
        return id;
    }

    public final Date creationDate() {
        return creationDate;
    }

    public final TicketEntry withDataUpdated(Ticket data) {
        return new TicketEntry(
            id(), 
            creationDate(), 
            data
        );
    }

    @Override
    public String toString() {
        return String.format(
            "#%s, %s: %s",
            id, creationDate, super.toString()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false; 
        TicketEntry that = (TicketEntry) other;
        return id == that.id 
            && creationDate.equals(that.creationDate) 
            && super.equals(that);
    }

    @Override
    public int hashCode() {
        return id + 
            31 * (creationDate.hashCode() + 
            31 * super.hashCode());
    }
}
