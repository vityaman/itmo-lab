package ru.vityaman.tidb.data.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Valid location.
 */
public interface Location extends JsonObject, Representable {
    /**
     * @return x
     */
    float x();

    /**
     * @return y
     */
    double y();

    /**
     * @return z
     */
    float z();

    /**
     * @return name
     */
    String name();

    @Override
    default Map<String, Object> json() {
        return new HashMap<String, Object>() {{
            put("x", x());
            put("y", y());
            put("z", z());
            put("name", name());
        }};
    }

    @Override
    default String representation() {
        return new StringBuilder()
            .append("{")
            .append(String.format(" name: %s,", name()))
            .append(String.format(" x: %s,", x()))
            .append(String.format(" y: %s,", y()))
            .append(String.format(" z: %s ", z()))
            .append("}")
            .toString();
    }

    final class RequireValid {
        public static float x(Float x) {
            return x;
        }

        public static double y(double y) {
            return y;
        }

        public static float z(float z) {
            return z;
        }

        public static String name(String name) {
            return Objects.requireNonNull(name);
        }

        private RequireValid() { throw new AssertionError("Utility class"); }
    }
}
