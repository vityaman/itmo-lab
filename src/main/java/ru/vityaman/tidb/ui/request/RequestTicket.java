package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.data.dto.TicketData;
import ru.vityaman.tidb.data.model.*;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Colored;
import ru.vityaman.tidb.ui.out.Prefixed;
import ru.vityaman.tidb.ui.out.Out;

import static ru.vityaman.tidb.ui.out.ConsoleColor.PURPLE;

import java.util.HashSet;

/**
 * Class requests ticket from user.
 */
public final class RequestTicket implements RequestInput<Ticket> {
    private static final RequestInput<String> NAME_REQUEST
            = new RequestPrimitive<>(
                "name: ",
                (string) -> Ticket.RequireValid.name(string),
            new HashSet<Class<? extends Exception>>() {{
                add(InvalidValueException.class);
            }});
    private static final RequestInput<Coordinates> COORDINATES_REQUEST
            = new RequestCoordinates();
    private static final RequestInput<Integer> PRICE_REQUEST
            = new RequestPrimitive<>(
                "price: ",
                (string) -> {
                    if (string.isEmpty()) return null;
                    return Ticket.RequireValid.price(Integer.parseInt(string));
                },
            new HashSet<Class<? extends Exception>>() {{
                add(NumberFormatException.class);
                add(InvalidValueException.class);
            }});

    private static final RequestInput<TicketType> TYPE_REQUEST
            = new RequestPrimitive<>(
            "type: ",
            (string) -> {
                try {
                   return TicketType.valueOf(string);
                } catch (IllegalArgumentException e) {
                    throw new InvalidValueException(
                            "Ticket type can be one of: VIP, USUAL, CHEAP");
                }
            },
            new HashSet<Class<? extends Exception>>() {{
                add(InvalidValueException.class);
            }});
    private static final RequestInput<Person> PERSON_REQUEST
            = new RequestPerson();

    @Override
    public Ticket from(Input in, Out out) {
        TicketData.Builder ticket = TicketData.builder();

        out.print("ticket: " + PURPLE.wrapped("{\n"));

        // TODO: from(in, out) repetition
        Out prefixed = new Colored(PURPLE, new Prefixed("    ", out));
        ticket.withName(NAME_REQUEST.from(in, prefixed))
              .withCoordinates(COORDINATES_REQUEST.from(in, prefixed))
              .withPrice(PRICE_REQUEST.from(in, prefixed))
              .withType(TYPE_REQUEST.from(in, prefixed))
              .withPerson(PERSON_REQUEST.from(in, prefixed));

        out.print(PURPLE.wrapped("}\n"));

        return ticket.instance();
    }
}
