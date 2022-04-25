package ru.vityaman.tidb.data.model;

import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import java.util.Date;
import java.util.Map;
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
    default Map<String, Object> json() {
        Map<String, Object> json = Ticket.super.json();
        json.put("id", id());
        json.put("creationDate", creationDate());
        return json;
    }

    @Override
    default String representation() {
        return new StringBuilder()
            .append("{")
            .append(String.format(" id: %s,", id()))
            .append(String.format(" created: %s,", creationDate()))
            .append(String.format(" name: %s,", name()))
            .append(String.format(" coordinates: %s,", coordinates().representation()))
            .append((price().isPresent()) ? (" price: " + price().get() + ",") : (""))
            .append(String.format(" type: %s,", type()))
            .append(String.format(" person: %s ", person().representation()))
            .append("}")
            .toString();
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
