package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.dto.PersonData;
import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.data.model.Person;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Colored;
import ru.vityaman.tidb.ui.out.Prefixed;
import ru.vityaman.tidb.ui.out.Out;

import static ru.vityaman.tidb.ui.out.ConsoleColor.*;

import java.util.HashSet;

/**
 * Requests person from user.
 */
public final class RequestPerson implements RequestInput<Person> {
    private static final RequestInput<Integer> HEIGHT_REQUEST
            = new RequestPrimitive<>(
            "height: ",
            (string) -> Person.RequireValid.height(Integer.parseInt(string)),
            new HashSet<Class<? extends Exception>>() {{
                add(NumberFormatException.class);
                add(InvalidValueException.class);
            }});

    private static final RequestInput<String> PASSPORT_ID_REQUEST
            = new RequestPrimitive<>(
            "passportId: ",
            (string) -> Person.RequireValid.passportId(string),
            new HashSet<Class<? extends Exception>>() {{
                add(InvalidValueException.class);
            }});
    private static final RequestInput<Location> LOCATION_REQUEST
            = new RequestLocation();

    @Override
    public Person from(Input in, Out out) {
        PersonData.Builder person = PersonData.builder();

        out.print("person: " + YELLOW.wrapped("{\n"));

        Out prefixed = new Colored(YELLOW, new Prefixed("    ", out));
        person.withHeight(HEIGHT_REQUEST.from(in, prefixed))
              .withPassportId(PASSPORT_ID_REQUEST.from(in, prefixed))
              .withLocation(LOCATION_REQUEST.from(in, prefixed));

        out.print(YELLOW.wrapped("}\n"));

        return person.instance();
    }
}
