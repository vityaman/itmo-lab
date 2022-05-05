package ru.vityaman.tidb.ui.input;

import static ru.vityaman.tidb.ui.out.ConsoleColor.*;
import ru.vityaman.tidb.collection.data.Coordinates;
import ru.vityaman.tidb.collection.data.Location;
import ru.vityaman.tidb.collection.data.Person;
import ru.vityaman.tidb.collection.data.Ticket;
import ru.vityaman.tidb.collection.data.TicketType;
import ru.vityaman.tidb.ui.out.Colored;
import ru.vityaman.tidb.ui.out.Out;
import ru.vityaman.tidb.ui.out.Prefixed;
import static ru.vityaman.tidb.ui.input.RequestPrimitive.*;

public final class RequestObject {
    private final Input in;
    private final Out out;

    public RequestObject(Input in, Out out) {
        this.in = in;
        this.out = out;
    }

    public Coordinates coordinates() {
        Coordinates.Builder coordinates = Coordinates.fresh();
        out.print("coordinates: " + BLUE.wrapped("{\n"));
        Out inner = new Colored(BLUE, new Prefixed("    ", out));
        RequestPrimitive primitive = new RequestPrimitive(in, inner);
        primitive.request(
            doubleFor("x"), 
            coordinates::withX
        );
        primitive.request(
            doubleFor("y"),
            coordinates::withY
        );  
        out.print(BLUE.wrapped("}\n"));
        return coordinates.instance();
    }

    public Location location() {
        Location.Builder location = Location.fresh();
        out.print("location: " + CYAN.wrapped("{\n"));
        Out inner = new Colored(CYAN, new Prefixed("    ", out));
        RequestPrimitive primitive = new RequestPrimitive(in, inner);
        primitive.request(stringFor("name"), location::withName);
        primitive.request(floatFor("x"), location::withX);
        primitive.request(doubleFor("y"), location::withY);
        primitive.request(floatFor("z"), location::withZ);
        out.print(CYAN.wrapped("}\n"));
        return location.instance();
    }

    public Person person() {
        Person.Builder person = Person.fresh();
        out.print("person: " + YELLOW.wrapped("{\n"));
        Out inner = new Colored(YELLOW, new Prefixed("    ", out));
        RequestPrimitive primitive = new RequestPrimitive(in, inner);
        RequestObject object = new RequestObject(in, inner);
        primitive.request(intFor("height"), person::withHeight);
        primitive.request(stringFor("passport"), person::withPassportId);
        person.withLocation(object.location());
        out.print(YELLOW.wrapped("}\n"));
        return person.instance();
    }

    public Ticket ticket() {
        Ticket.Builder ticket = Ticket.fresh();
        out.print("ticket: " + PURPLE.wrapped("{\n"));
        Out inner = new Colored(PURPLE, new Prefixed("    ", out));
        RequestPrimitive primitive = new RequestPrimitive(in, inner);
        RequestObject request = new RequestObject(in, inner);
        primitive.request(stringFor("name"), ticket::withName);
        ticket.withCoordinates(request.coordinates());
        primitive.request(stringFor("price"), (s) -> {
            // sorry, Egor Bugaenko...
            if (!s.isEmpty()) ticket.withPrice(Integer.parseInt(s));
        });
        primitive.request(
            enumFor("type", TicketType.class), 
            ticket::withType
        );
        ticket.withPerson(request.person());
        out.print(PURPLE.wrapped("}\n"));
        return ticket.instance();
    }
}
