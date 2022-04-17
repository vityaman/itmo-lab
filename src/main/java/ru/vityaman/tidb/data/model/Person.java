package ru.vityaman.tidb.data.model;

import ru.vityaman.tidb.data.model.exception.InvalidValueException;

import java.util.HashMap;
import java.util.Map;

/**
 * Valid person.
 */
public interface Person extends DataObject {
    /**
     * @return height.
     */
    int height();

    /**
     * @return passportId.
     */
    String passportId();

    /**
     * @return location.
     */
    Location location();

    @Override
    default Map<String, Object> json() {
        return new HashMap<String, Object>() {{
            put("height", height());
            put("passportId", passportId());
            put("location", location().json());
        }};
    }

    @Override
    default String repr() {
        return String.format("id: %s, height: %s, pos: %s)",
                passportId(), height(), location().repr());
    }

    final class RequireValid {
        public static int height(int height) {
            if (height <= 0) {
                throw new InvalidValueException(
                    "Person.height must be positive");
            }
            return height;
        }

        public static String passportId(String passportId) {
            if (passportId.length() < 5 || 47 < passportId.length()) {
                throw new InvalidValueException(
                    "Person.passportId.length must be in [5, 47]");
            }
            return passportId;
        }

        private RequireValid() { throw new AssertionError("Utility class"); }
    }
}
