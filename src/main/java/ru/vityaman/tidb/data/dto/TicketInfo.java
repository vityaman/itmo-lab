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
public final class TicketInfo implements Ticket {
     private final String name;
     private final Coordinates coordinates;
     private final Integer price;
     private final TicketType type;
     private final Person person;

     public String name() {
          return name;
     }

     public Coordinates coordinates() {
          return coordinates;
     }

     public Optional<Integer> price() {
          return Optional.ofNullable(price);
     }

     public TicketType type() {
          return type;
     }

     public Person person() {
          return person;
     }

     public static Builder builder() {
          return new Builder();
      }

     public static final class Builder {
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

          public TicketInfo instance() {
               return new TicketInfo(this);
          }
     }

     private TicketInfo(Builder builder) {
          name = Objects.requireNonNull(builder.name);
          coordinates = Objects.requireNonNull(builder.coordinates);
          price = builder.price;
          type = Objects.requireNonNull(builder.type);
          person = Objects.requireNonNull(builder.person);
     }
}
