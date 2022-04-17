package ru.vityaman.tidb.data.model;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Valid location.
 */
public interface Location extends DataObject {
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
    default String repr() {
        return String.format("%s (%.2f, %.2f, %.2f)", name(), x(), y(), z());
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
