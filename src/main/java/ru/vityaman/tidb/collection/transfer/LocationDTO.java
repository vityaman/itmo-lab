package ru.vityaman.tidb.collection.transfer;

public class LocationDTO {
    private String name;
    private Float x;
    private Double y;
    private Float z;

    public LocationDTO(String name, Float x, Double y, Float z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final String name() {
        return name;
    }

    public final Float x() {
        return x;
    }

    public final Double y() {
        return y;
    }

    public final Float z() {
        return z;
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
        if (other == null || getClass() != other.getClass()) return false;
        LocationDTO that = (LocationDTO) other;
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

    public static Another another() {
        return new Another(new LocationDTO());
    }

    public static Another another(LocationDTO other) {
        return new Another(other);
    }

    public static final class Another {
        private final LocationDTO result;

        private Another(LocationDTO init) {
            result = new LocationDTO(init);
        }

        public Another withName(String name) {
            result.name = name;
            return this;
        }

        public Another withX(Float x) {
            result.x = x;
            return this;
        }

        public Another withY(Double y) {
            result.y = y;
            return this;
        }

        public Another withZ(Float z) {
            result.z = z;
            return this;
        }

        public LocationDTO instance() {
            return result;
        }
    }

    private LocationDTO() {}

    protected LocationDTO(LocationDTO other) {
        this(
            other.name, 
            other.x, 
            other.y, 
            other.z
        );
    }
}
