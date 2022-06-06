package ru.vityaman.tidb.api.data;


public final class Coordinates {
    private final double x;
    private final double y;

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public String toString() {
        return String.format(
            "(%s, %s)", 
            x, y
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false; 
        Coordinates that = (Coordinates) other;
        return x == that.x 
            && y == that.y;
    }

    @Override
    public int hashCode() {
        return (int)(x + 31 * y);
    }

    public static Builder fresh() {
        return new Builder();
    }

    public static Builder updated(Coordinates coordinates) {
        return new Builder(coordinates);
    }

    public static final class Builder {
        private Double x;
        private Double y;

        private Builder() {}

        private Builder(Coordinates coordinates) {
            x = coordinates.x;
            y = coordinates.y;
        }

        public Builder withX(double x) {
            this.x = x; 
            return this;
        }

        public Builder withY(double y) {
            if (762 < y) {
                throw new IllegalArgumentException(
                    "y max value is 762"
                );
            }
            this.y = y;
            return this;
        }

        public Coordinates instance() {
            return new Coordinates(this);
        }
    }

    private Coordinates(Builder builder) {
        if (builder.x == null || builder.y == null)  {
            throw new IllegalStateException(String.format(
                    "x = %s, y = %s", 
                    builder.x, builder.y
                )                
            );
        }
        x = builder.x;
        y = builder.y;
    }
}
