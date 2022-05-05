package ru.vityaman.tidb.collection.data;

import java.util.Objects;
import java.util.Optional;

import ru.vityaman.tidb.collection.data.exception.InvalidFieldValueException;


public class Ticket {
     private final String name;
     private final Coordinates coordinates;
     private final Integer price;
     private final TicketType type;
     private final Person person;

     public final String name() {
          return name;
     }

     public final Coordinates coordinates() {
          return coordinates;
     }

     public final Optional<Integer> price() {
          return Optional.ofNullable(price);
     }

     public final TicketType type() {
          return type;
     }

     public final Person person() {
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
          Ticket that = (Ticket) other;
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

     public static Builder fresh() {
          return new Builder();
     }

     public static Builder updated(Ticket ticket) {
          return new Builder(ticket);
     }

     public static class Builder {
          private String name;
          private Coordinates coordinates;
          private Integer price;
          private TicketType type;
          private Person person;

          Builder() {}

          Builder(Ticket ticket) {
               name = ticket.name;
               coordinates = ticket.coordinates;
               price = ticket.price;
               type = ticket.type;
               person = ticket.person;
          }

          public Builder withName(String name) throws InvalidFieldValueException {
               if (name == null || name.isEmpty()) {
                    throw new InvalidFieldValueException(
                        "name must be non empty"
                    );
                }
               this.name = name;
               return this;
          }

          public Builder withCoordinates(Coordinates coordinates) {
               this.coordinates = coordinates;
               return this;
          }

          public Builder withPrice(Integer price) throws InvalidFieldValueException {
               if (price != null && price <= 0) {
                    throw new InvalidFieldValueException(
                         "price must be positive"
                    );
               }
               this.price = price;
               return this;
          }

          public Builder withType(TicketType type) {
               this.type = type;
               return this;
          }

          public Builder withPerson(Person person) {
               this.person = person;
               return this;
          }

          public Ticket instance() {
               return new Ticket(this);
          }
     }

     protected Ticket(Ticket ticket) {
          this(Ticket.updated(ticket));
     }

     private Ticket(Builder builder) {
          if (builder.name == null || builder.coordinates == null
           || builder.type == null || builder.person == null) {
               throw new IllegalStateException(String.format(
                         "name: %s, coordinates: %s, price: %s, " +
                         "type: %s, person: %s",
                         builder.name, builder.coordinates, 
                         builder.price, builder.type, builder.person
                    )
               );
           }
          name = builder.name;
          coordinates = builder.coordinates;
          price = builder.price;
          type = builder.type;
          person = builder.person;
     }
}
