package ru.vityaman.tidb.collection.transfer;

import java.util.Date;

public class TicketEntryDTO {
    private final Integer id;
    private final Date creationDate;
    private final TicketDTO ticket;

    public TicketEntryDTO(Integer id, Date creationDate, TicketDTO ticket) {
        this.id = id;
        this.creationDate = creationDate;
        this.ticket = ticket;
    }

    public final Integer id() {
        return id;
    }

    public final Date creationDate() {
        return creationDate;
    }

    public TicketDTO ticket() {
        return ticket;
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
        if (other == null || getClass() != other.getClass()) return false; 
        TicketEntryDTO that = (TicketEntryDTO) other;
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

    protected TicketEntryDTO(TicketEntryDTO other) {
        this(
            other.id,
            new Date(
                other.creationDate.getTime()
            ),
            other.ticket
        );
    }
}
