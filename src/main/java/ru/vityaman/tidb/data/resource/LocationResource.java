package ru.vityaman.tidb.data.resource;

import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.data.resource.exception.ResourceException;

public interface LocationResource extends Location {
    void setX(float x) throws ResourceException;
    void setY(double y) throws ResourceException;
    void setZ(float z) throws ResourceException;
    void setName(String name) throws ResourceException;
}
