package ru.vityaman.tidb.data.resource;


import ru.vityaman.tidb.data.model.Person;

public interface PersonResource extends Person {
    @Override LocationResource location();

    void setHeight(int height);
    void setPassportId(String passportId);
}
