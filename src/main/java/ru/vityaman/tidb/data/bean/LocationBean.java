package ru.vityaman.tidb.data.bean;

import ru.vityaman.tidb.data.model.Location;

/**
 * Location Bean.
 */
public final class LocationBean {
    private Float x;
    private Double y;
    private Float z;
    private String name;

    public float x() {
        return x;
    }

    public void setX(Float x) {
        this.x = Location.RequireValid.x(x);
    }

    public double y() {
        return y;
    }

    public void setY(double y) {
        this.y = Location.RequireValid.y(y);
    }

    public float z() {
        return z;
    }

    public void setZ(Float z) {
        this.z = Location.RequireValid.z(z);
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = Location.RequireValid.name(name);
    }

    /**
     * @return validated location.
     */
    public Location validated() {
        Location.RequireValid.x(x);
        Location.RequireValid.y(y);
        Location.RequireValid.z(z);
        Location.RequireValid.name(name);
        return new Location() {
            @Override public float x()
            { return x; }
            @Override public double y()
            { return y; }
            @Override public float z()
            { return z; }
            @Override public String name()
            { return name; }
        };
    }
}
