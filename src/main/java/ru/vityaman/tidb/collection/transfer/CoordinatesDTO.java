package ru.vityaman.tidb.collection.transfer;

public class CoordinatesDTO {
    private Double x;    
    private Double y;

    public CoordinatesDTO(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public final Double x() {
        return x;
    }

    public final Double y() {
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
        if (other == null || getClass() != other.getClass()) return false; 
        CoordinatesDTO that = (CoordinatesDTO) other;
        return x == that.x 
            && y == that.y;
    }

    @Override
    public int hashCode() {
        return (int)(x + 31 * y);
    }

    public static Another another() {
        return new Another(new CoordinatesDTO());
    }

    public static Another another(CoordinatesDTO other) {
        return new Another(other);
    }

    public static final class Another {
        private final CoordinatesDTO result;

        private Another(CoordinatesDTO init) {
            result = new CoordinatesDTO(init);
        }

        public Another withX(Double x) {
            result.x = x;
            return this;
        }

        public Another withY(Double y) {
            result.y = y;
            return this;
        }

        public CoordinatesDTO instance() {
            return result;
        }
    }

    private CoordinatesDTO() {}

    protected CoordinatesDTO(CoordinatesDTO other) {
        this(
            other.x, 
            other.y
        );
    }
}
