package ru.vityaman.tidb.collection.data.valid.entity;

import ru.vityaman.tidb.collection.data.valid.validation.TicketValidator;
import ru.vityaman.tidb.collection.data.valid.validation.exception.InvalidFieldValueException;
import ru.vityaman.tidb.collection.data.valid.validation.exception.ValidationException;
import ru.vityaman.tidb.collection.transfer.TicketDTO;

public final class Ticket extends TicketDTO {
    @Override
    public Coordinates coordinates() {
        return (Coordinates) super.coordinates();
    }

    @Override
    public Person person() {
        return (Person) super.person();
    }

    public static final class Factory {
        private final Coordinates.Factory coordinatesFactory;
        private final Person.Factory personFactory;
        private final TicketValidator validator;

        public Factory(
            Coordinates.Factory coordinatesFactory,
            Person.Factory personFactory,
            TicketValidator validator
        ) {
            this.coordinatesFactory = coordinatesFactory;
            this.personFactory = personFactory;
            this.validator = validator;
        }

        public Ticket newInstanceFrom(TicketDTO ticket) 
        throws ValidationException 
        {
            validator.ensureValidShadow(ticket);
            return new Ticket(
                TicketDTO.another(ticket)
                    .withCoordinates(
                        coordinatesFactory.newInstanceFrom(
                            ticket.coordinates()
                        )
                    )      
                    .withPerson(
                        personFactory.newInstanceFrom(
                            ticket.person()
                        )
                    )
                    .instance()          
            );
        }

        public Another another() {
            return new Another();
        }

        public final class Another {
            private final TicketDTO.Another builder;

            private Another() {
                builder = TicketDTO.another();
            }

            public Another withName(String name) throws InvalidFieldValueException {
                validator.ensureValidName(name);
                builder.withName(name);
                return this;
            }
    
            public Another withCoordinates(Coordinates coordinates) {
                validator.ensureNotNull("coordinates", coordinates);
                builder.withCoordinates(coordinates);
                return this;
            }
    
            public Another withPrice(Integer price) throws InvalidFieldValueException {
                validator.ensureValidPrice(price);
                builder.withPrice(price);
                return this;
            }
    
            public Another withType(TicketType type) throws InvalidFieldValueException {
                validator.ensureValidType(type);
                builder.withType(type);
                return this;
            }
    
            public Another withPerson(Person person) {
                validator.ensureNotNull("person", person);
                builder.withPerson(person);
                return this;
            }
    
            public Ticket instance() {
                try {
                    return newInstanceFrom(
                        builder.instance()
                    );
                } catch (ValidationException e) {
                    throw new IllegalStateException(e);
                }
            }

        }
    }

    private Ticket(TicketDTO other) {
        super(other);
    }
}
