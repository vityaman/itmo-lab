package ru.vityaman.tidb.data.model;

import ru.vityaman.tidb.data.json.StringDate;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;

import java.util.Date;
import java.util.Objects;

/**
 * Valid ticket entry.
 */
public interface TicketEntry extends Ticket {
    /**
     * @return id.
     */
    int id();

    /**
     * @return creationDate.
     */
    Date creationDate();

    @Override
    default String repr() {
        return String.format(
                "| <%s> %d: %s",
                new StringDate(creationDate()).asString(),
                id(), Ticket.super.repr());
    }

    final class RequireValid {
        public static int id(int id) {
            if (id <= 0) {
                throw new InvalidValueException(
                    "TicketEntry.id must be positive");
            }
            return id;
        }

        public static Date creationDate(Date creationDate) {
            return Objects.requireNonNull(creationDate);
        }

        private RequireValid() { throw new AssertionError("Utility class"); }
    }
}
