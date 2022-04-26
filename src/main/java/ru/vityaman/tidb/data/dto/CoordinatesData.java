package ru.vityaman.tidb.data.dto;

import java.util.Objects;

import ru.vityaman.tidb.data.model.Coordinates;

/**
 * Coordinates Bean.
 */
public final class CoordinatesData implements Coordinates {
    private final double x;
    private final double y;

    public CoordinatesData(Coordinates other) {
        x = other.x();
        y = other.y();
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Double x;
        private Double y;

        Builder() {}

        public Builder withX(double x) {
            this.x = Coordinates.RequireValid.x(x);
            return this;
        }

        public Builder withY(double y) {
            this.y = Coordinates.RequireValid.y(y);
            return this;
        }

        public CoordinatesData instance() {
            return new CoordinatesData(this);
        }
    }

    private CoordinatesData(Builder builder) {
        // TODO: custom exception
        x = Objects.requireNonNull(builder.x);
        y = Objects.requireNonNull(builder.y);
    }
}
