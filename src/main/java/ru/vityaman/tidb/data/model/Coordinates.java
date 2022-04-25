package ru.vityaman.tidb.data.model;

import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import java.util.HashMap;
import java.util.Map;

/**
 * Valid coordinates.
 */
public interface Coordinates extends JsonObject, Representable {
    /**
     * @return x
     */
    double x();

    /**
     * @return y
     */
    double y();

    @Override
    default Map<String, Object> json() {
        return new HashMap<String, Object>() {{
            put("x", x());
            put("y", y());
        }};
    }

    @Override
    default String representation() {
        return new StringBuilder()
            .append("{")
            .append(String.format(" x: %s,", x()))
            .append(String.format(" y: %s ", y()))
            .append("}")
            .toString();
    }

    final class RequireValid {
        public static double x(double x) {
            return x;
        }

        public static double y(double y) {
            if (762 < y) {
                throw new InvalidValueException(
                    "Coordinates.y max value is 762");
            }
            return y;
        }

        private RequireValid() { throw new AssertionError("Utility class"); }
    }
}
