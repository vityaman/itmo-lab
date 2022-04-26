package ru.vityaman.tidb.data.dto;

import ru.vityaman.tidb.data.model.Coordinates;
import ru.vityaman.tidb.data.model.Person;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketType;

import java.util.Objects;
import java.util.Optional;

/**
 * Ticket Bean.
 */
public class TicketData implements Ticket {
     private final String name;
     private final Coordinates coordinates;
     private final Integer price;
     private final TicketType type;
     private final Person person;

     public TicketData(Ticket other) {
          name = other.name();
          coordinates = new CoordinatesData(other.coordinates());
          price = other.price().orElse(null);
          type = other.type();
          person = new PersonData(other.person());
     }

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

     public static Builder builder() {
          return new Builder();
     }

     public static class Builder {
          private String name;
          private Coordinates coordinates;
          private Integer price;
          private TicketType type;
          private Person person;

          Builder() {}

          public Builder withName(String name) {
               this.name = Ticket.RequireValid.name(name);
               return this;
          }

          public Builder withCoordinates(Coordinates coordinates) {
               this.coordinates = coordinates;
               return this;
          }

          public Builder withPrice(Integer price) {
               if (price != null) {
                    this.price = Ticket.RequireValid.price(price);
               }
               return this;
          }

          public Builder withType(TicketType type) {
               this.type = Ticket.RequireValid.type(type);
               return this;
          }

          public Builder withPerson(Person person) {
               this.person = person;
               return this;
          }

          public TicketData instance() {
               return new TicketData(this);
          }
     }

     private TicketData(Builder builder) {
          name = Objects.requireNonNull(builder.name);
          coordinates = Objects.requireNonNull(builder.coordinates);
          price = builder.price;
          type = Objects.requireNonNull(builder.type);
          person = Objects.requireNonNull(builder.person);
     }
}
