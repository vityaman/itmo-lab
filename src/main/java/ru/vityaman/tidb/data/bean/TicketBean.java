package ru.vityaman.tidb.data.bean;

import ru.vityaman.tidb.data.model.Coordinates;
import ru.vityaman.tidb.data.model.Person;
import ru.vityaman.tidb.data.model.Ticket;
import ru.vityaman.tidb.data.model.TicketType;

import java.util.Objects;
import java.util.Optional;

/**
 * Ticket Bean.
 */
public final class TicketBean {
     private String name;
     private Coordinates coordinates;
     private Integer price;
     private TicketType type;
     private Person person;

     public TicketBean() {}

     /**
      * @param ticket original
      */
     public TicketBean(Ticket ticket) {
          name = ticket.name();
          coordinates = ticket.coordinates();
          price = ticket.price().orElse(null);
          type = ticket.type();
          person = ticket.person();
     }

     public String name() {
          return name;
     }

     public void setName(String name) {
          this.name = Ticket.RequireValid.name(name);
     }

     public Coordinates coordinates() {
          return coordinates;
     }

     public void setCoordinates(Coordinates coordinates) {
          this.coordinates = coordinates;
     }

     public Optional<Integer> price() {
          return Optional.ofNullable(price);
     }

     public void setPrice(Integer price) {
          if (price != null) {
               this.price = Ticket.RequireValid.price(price);
          } else {
               this.price = null;
          }
     }

     public TicketType type() {
          return type;
     }

     public void setType(TicketType type) {
          this.type = Ticket.RequireValid.type(type);
     }

     public Person person() {
          return person;
     }

     public void setPerson(Person person) {
          this.person = person;
     }

     /**
      * @return validated ticket
      */
     public Ticket validated() {
          Ticket.RequireValid.name(name);
          Ticket.RequireValid.type(type);
          if (price != null) Ticket.RequireValid.price(price);
          Objects.requireNonNull(coordinates);
          Objects.requireNonNull(type);
          Objects.requireNonNull(person);
          return new Ticket() {
               @Override public String name()
               { return name; }
               @Override public Coordinates coordinates()
               { return coordinates; }
               @Override public Optional<Integer> price()
               { return Optional.ofNullable(price); }
               @Override public TicketType type()
               { return type; }
               @Override public Person person()
               { return person; }
          };
     }
}
