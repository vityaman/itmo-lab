package ru.vityaman.tidb.data.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import ru.vityaman.tidb.data.model.exception.InvalidValueException;


public interface Ticket {
    String name();
    Coordinates coordinates();
    Optional<Integer> price();
    TicketType type();
    Person person();

    default Map<String, Object> json() {
        HashMap<String, Object> json = new HashMap<String, Object>() {{
                put("name", name());
                put("coordinates", coordinates().json());
                put("type", type().toString());
                put("person", person().json());
        }};
        if (price().isPresent()) {
            json.put("price", price().get());
        }
        return json;
    }

    default String representation() {
        return new StringBuilder()
            .append("{")
            .append(String.format(" name: %s,", name()))
            .append(String.format(" coordinates: %s,", coordinates().representation()))
            .append((price().isPresent()) ? (" price: " + price().get() + ",") : (""))
            .append(String.format(" type: %s,", type()))
            .append(String.format(" person: %s ", person().representation()))
            .append("}")
            .toString();
    }

    final class RequireValid {
        public static String name(String name) {
            if (name == null) {
                throw new InvalidValueException(
                    "Ticket.name must be present");
            }
            if (name.isEmpty()) {
                throw new InvalidValueException(
                    "Ticket.name must be non empty");
            }
            return name;
        }

        public static int price(int price) {
            if (price <= 0) {
                throw new InvalidValueException(
                "Ticket.price must be positive");
            }
            return price;
        }

        public static TicketType type(TicketType type) {
            return Objects.requireNonNull(type);
        }

        private RequireValid() { throw new AssertionError("Utility class"); }
    }
}
