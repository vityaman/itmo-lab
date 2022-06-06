package ru.vityaman.tidb.api.collection;

import ru.vityaman.tidb.api.data.TicketEntry;

public interface Entry {
    String key();
    TicketEntry ticket();
}
