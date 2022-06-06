package ru.vityaman.tidb.api.data;

import ru.vityaman.tidb.api.collection.Entry;

public class SimpleEntry implements Entry {
    private final String key;
    private final TicketEntry ticket;

    public SimpleEntry(String key, TicketEntry ticket) {
        this.key = key;
        this.ticket = ticket;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public TicketEntry ticket() {
        return ticket;
    }

    @Override
    public String toString() {
        return String.format(
            "\"%s\"': %s",
            key, ticket
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        SimpleEntry that = (SimpleEntry) other;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    
}
