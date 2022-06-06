package ru.vityaman.tidb.api.data;

public final class Location {
    private final float x;
    private final double y;
    private final float z;
    private final String name;

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

    @Override
    public String toString() {
        return String.format(
            "%s (%s, %s, %s)", 
            name, x, y, z
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Location that = (Location) other;
        return x == that.x 
            && y == that.y 
            && z == that.z
            && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return (int)(name.hashCode() + 
            31 * (x + 
            31 * (y + 
            31 *  z)));
    }

    public static Builder fresh() {
        return new Builder();
    }

    public static Builder updated(Location location) {
        return new Builder(location);
    }

    public static final class Builder {
        private Float x;
        private Double y;
        private Float z;
        private String name;

        private Builder() {}

        private Builder(Location location) {
            x = location.x;
            y = location.y;
            z = location.z;
            name = location.name;
        }

        public Builder withX(float x) {
            this.x = x;
            return this;
        }

        public Builder withY(double y) {
            this.y = y;
            return this;
        }

        public Builder withZ(float z) {
            this.z = z;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Location instance() {
            return new Location(this);
        }
    }

    private Location(Builder builder) {
        if (builder.x == null || builder.y == null
         || builder.z == null || builder.name == null) {
            throw new IllegalStateException(
                String.format(
                    "name: %s, x: %s, y: %s, z: %s",
                    builder.name, builder.x, builder.y, builder.z
                )
            );    
        }
        x = builder.x;
        y = builder.y;
        z = builder.z;
        name = builder.name;
    }
}
