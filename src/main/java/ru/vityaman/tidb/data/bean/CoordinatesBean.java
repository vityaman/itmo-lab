package ru.vityaman.tidb.data.bean;

import ru.vityaman.tidb.data.model.Coordinates;

/**
 * Coordinates Bean.
 */
public final class CoordinatesBean {
    private Double x;
    private Double y;

    public CoordinatesBean() {}

    public double x() {
        return x;
    }

    public void setX(double x) {
        this.x = Coordinates.RequireValid.x(x);
    }

    public double y() {
        return y;
    }

    public void setY(double y) {
        this.y = Coordinates.RequireValid.y(y);
    }

    /**
     * @return validated coordinates
     */
    public Coordinates validated() {
        Coordinates.RequireValid.x(x);
        Coordinates.RequireValid.y(y);
        return new Coordinates() {
            @Override public double x()
            { return x; }
            @Override public double y()
            { return y; }
        };
    }
}
