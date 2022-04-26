package ru.vityaman.tidb.data.resource;

import ru.vityaman.tidb.data.model.Coordinates;
import ru.vityaman.tidb.data.resource.exception.ResourceException;

public interface CoordinatesResource extends Coordinates {
    void setX(double x) throws ResourceException;
    void setY(double y) throws ResourceException;
}
