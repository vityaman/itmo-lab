package ru.vityaman.tidb.data.resource;

import ru.vityaman.tidb.data.model.Person;
import ru.vityaman.tidb.data.resource.exception.ResourceException;

public interface PersonResource extends Person {
    @Override LocationResource location();

    void setHeight(int height) throws ResourceException;
    void setPassportId(String passportId) throws ResourceException;
}
