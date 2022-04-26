package ru.vityaman.tidb.data.dto;

import java.util.Objects;

import ru.vityaman.tidb.data.model.Location;

/**
 * Location Bean.
 */
public final class LocationData implements Location {
    private final float x;
    private final double y;
    private final float z;
    private final String name;

    public LocationData(Location other) {
        x = other.x();
        y = other.y();
        z = other.z();
        name = other.name();
    }

    public float x() {
        return x;
    }

    public double y() {
        return y;
    }

    public float z() {
        return z;
    }

    public String name() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Float x;
        private Double y;
        private Float z;
        private String name;

        Builder() {}

        public Builder withX(float x) {
            this.x = Location.RequireValid.x(x);
            return this;
        }

        public Builder withY(double y) {
            this.y = Location.RequireValid.y(y);
            return this;
        }

        public Builder withZ(float z) {
            this.z = Location.RequireValid.z(z);
            return this;
        }

        public Builder withName(String name) {
            this.name = Location.RequireValid.name(name);
            return this;
        }

        public LocationData instance() {
            return new LocationData(this);
        }
    }

    private LocationData(Builder builder) {
        x = Objects.requireNonNull(builder.x);
        y = Objects.requireNonNull(builder.y);
        z = Objects.requireNonNull(builder.z);
        name = Objects.requireNonNull(builder.name);
    }
}
