package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.data.bean.PersonBean;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.data.model.Person;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Colored;
import ru.vityaman.tidb.ui.printer.Prefixed;
import ru.vityaman.tidb.ui.printer.Printer;

import java.util.HashSet;

import static ru.vityaman.tidb.ui.printer.ConsoleColor.*;

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
    public Person from(Input in, Printer out) {
        PersonBean person = new PersonBean();

        out.print("person: " + YELLOW.wrapped("{\n"));

        Printer prefixed = new Colored(YELLOW, new Prefixed("    ", out));
        person.setHeight(HEIGHT_REQUEST.from(in, prefixed));
        person.setPassportId(PASSPORT_ID_REQUEST.from(in, prefixed));
        person.setLocation(LOCATION_REQUEST.from(in, prefixed));

        out.print(YELLOW.wrapped("}\n"));

        return person.validated();
    }
}
