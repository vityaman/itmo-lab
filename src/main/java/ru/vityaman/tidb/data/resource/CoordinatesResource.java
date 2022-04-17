package ru.vityaman.tidb.data.resource;

import ru.vityaman.tidb.data.model.Coordinates;

public interface CoordinatesResource extends Coordinates {
    void setX(double x);
    void setY(double y);
}
