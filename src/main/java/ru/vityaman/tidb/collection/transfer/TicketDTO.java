package ru.vityaman.tidb.collection.transfer;

import java.util.Objects;

import ru.vityaman.tidb.collection.data.valid.entity.TicketType;

public class TicketDTO {
    private String name;
    private CoordinatesDTO coordinates;
    private Integer price;
    private TicketType type;
    private PersonDTO person;

    public TicketDTO(
        String name, CoordinatesDTO coordinates, 
        Integer price, TicketType type, 
        PersonDTO person
    ) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.type = type;
        this.person = person;
    }

    public TicketDTO(
        String name, CoordinatesDTO coordinates, 
        TicketType type, PersonDTO person
    ) {
        this(name, coordinates, null, type, person);
    }

    public final String name() {
        return name;
    }

    public CoordinatesDTO coordinates() {
        return coordinates;
    }

    public final Integer price() {
        return price;
    }

    public final TicketType type() {
        return type;
    }

    public PersonDTO person() {
        return person;
    }

    @Override
    public String toString() {
        return String.format(
            "Ticket [name: %s, coordinates: %s, " +
            "price: %s, type: %s, person: %s]",
            name, coordinates, price, type, person
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false; 
        TicketDTO that = (TicketDTO) other;
        return coordinates.equals(that.coordinates)
            && name.equals(that.name)
            && type.equals(that.type)
            && Objects.equals(price, that.price)
            && person.equals(that.person);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + 
            31 * (coordinates.hashCode() +
            31 * (Objects.hashCode(price) + 
            31 * (type.hashCode() + 
            31 * (person.hashCode()))));
    }

    public static Another another() {
        return new Another(new TicketDTO());
    }

    public static Another another(TicketDTO other) {
        return new Another(other);
    }

    public static final class Another {
        private final TicketDTO result;

        private Another(TicketDTO init) {
            result = new TicketDTO(init);
        }

        public Another withName(String name) {
            result.name = name;
            return this;
        }

        public Another withCoordinates(CoordinatesDTO coordinates) {
            result.coordinates = coordinates;
            return this;
        }

        public Another withPrice(Integer price) {
            result.price = price;
            return this;
        }

        public Another withType(TicketType type) {
            result.type = type;
            return this;
        }

        public Another withPerson(PersonDTO person) {
            result.person = person;
            return this;
        }

        public TicketDTO instance() {
            return result;
        }
    }

    private TicketDTO() {}

    protected TicketDTO(TicketDTO other) {
        this(
            other.name, 
            other.coordinates,
            other.price,
            other.type, 
            other.person
        );
    }
}
