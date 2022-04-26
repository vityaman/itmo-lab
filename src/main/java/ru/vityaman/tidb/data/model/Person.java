package ru.vityaman.tidb.data.model;

import java.util.HashMap;
import java.util.Map;

import ru.vityaman.tidb.data.model.exception.InvalidValueException;

public interface Person {
    int height();
    String passportId();
    Location location();

    default Map<String, Object> json() {
        return new HashMap<String, Object>() {{
            put("height", height());
            put("passportId", passportId());
            put("location", location().json());
        }};
    }

    default String representation() {
        return new StringBuilder()
            .append("{")
            .append(String.format(" height: %s,", height()))
            .append(String.format(" passportId: %s,", passportId()))
            .append(String.format(" location: %s ", location().representation()))
            .append("}")
            .toString();
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
