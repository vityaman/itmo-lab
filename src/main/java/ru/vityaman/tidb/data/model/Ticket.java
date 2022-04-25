package ru.vityaman.tidb.data.model;

import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.ui.out.Out;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Valid ticket.
 */
public interface Ticket extends JsonObject, Representable {
    /**
     * @return name.
     */
    String name();

    /**
     * @return coordinates.
     */
    Coordinates coordinates();

    /**
     * @return price.
     */
    Optional<Integer> price();

    /**
     * @return type.
     */
    TicketType type();

    /**
     * @return person.
     */
    Person person();

    @Override
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

    @Override
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
