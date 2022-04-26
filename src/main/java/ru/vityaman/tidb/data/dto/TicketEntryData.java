package ru.vityaman.tidb.data.dto;

import java.util.Date;
import java.util.Map;

import ru.vityaman.tidb.data.model.TicketEntry;

public final class TicketEntryData extends TicketData implements TicketEntry {
    private final int id;
    private final Date creationDate;

    public TicketEntryData(TicketEntry other) {
        super(other);
        id = other.id();
        creationDate = other.creationDate();
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Date creationDate() {
        return creationDate;
    }

    @Override
    public Map<String, Object> json() {
        return TicketEntry.super.json();
    }
}
