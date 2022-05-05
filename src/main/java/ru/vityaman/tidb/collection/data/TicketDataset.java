package ru.vityaman.tidb.collection.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ru.vityaman.tidb.collection.data.exception.InvalidFieldValueException;

public final class TicketDataset {
    private final int nextId;
    private final Map<String, TicketEntry> ticketsById;

    public TicketDataset() {
        nextId = 1;
        ticketsById = new HashMap<>();
    }

    public int nextId() {
        return nextId;
    }

    public Map<String, TicketEntry> ticketsByKey() {
        return Collections.unmodifiableMap(ticketsById);
    }

    public static Builder fresh() {
        return new Builder();
    }

    public static final class Builder {
        private Integer nextId;
        private final Map<String, TicketEntry> ticketsById;

        public Builder() {
            ticketsById = new HashMap<>();
        }

        public Builder withNextId(int nextId) throws InvalidFieldValueException {
            if (nextId <= 0) {
                throw new InvalidFieldValueException(String.format(
                        "nextId must be positive, not %s",
                        nextId
                    )
                );
            }
            this.nextId = nextId;
            return this;
        }

        public Builder withEntry(String key, TicketEntry entry) {
            this.ticketsById.put(key, entry);
            return this;
        }

        public TicketDataset instance() {
            if (nextId == null) {
                throw new IllegalStateException(String.format(
                    "nextId: %s",
                    nextId
                ));
            }
            return new TicketDataset(this);
        }
    }
    
    private TicketDataset(Builder builder) {
        nextId = builder.nextId;
        ticketsById = builder.ticketsById;
    }
}
