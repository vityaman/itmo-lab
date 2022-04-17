package ru.vityaman.tidb.data.resource;

import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketEntry;
import ru.vityaman.tidb.data.model.TicketType;


public interface TicketResource extends TicketEntry {
    @Override CoordinatesResource coordinates();
    @Override PersonResource person();

    void setName(String name);
    void setPrice(int price);
    void removePrice();
    void setType(TicketType type);

    default void updateUsing(Ticket other) {
        setName(other.name());
        if (other.price().isPresent()) {
            setPrice(other.price().get());
        } else {
            removePrice();
        }
        setType(other.type());
        coordinates().setX(other.coordinates().x());
        coordinates().setY(other.coordinates().y());
        person().setHeight(other.person().height());
        person().setPassportId(other.person().passportId());
        person().location().setName(other.person().location().name());
        person().location().setX(other.person().location().x());
        person().location().setY(other.person().location().y());
        person().location().setZ(other.person().location().z());
    }
}
