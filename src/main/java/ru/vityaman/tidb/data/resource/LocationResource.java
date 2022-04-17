package ru.vityaman.tidb.data.resource;

import ru.vityaman.tidb.data.model.Location;

public interface LocationResource extends Location {
    void setX(float x);
    void setY(double y);
    void setZ(float z);
    void setName(String name);
}
