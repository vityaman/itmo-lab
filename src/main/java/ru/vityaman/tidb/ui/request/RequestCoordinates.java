package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.data.bean.CoordinatesBean;
import ru.vityaman.tidb.data.model.Coordinates;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Colored;
import ru.vityaman.tidb.ui.printer.Prefixed;
import ru.vityaman.tidb.ui.printer.Printer;

import java.util.HashSet;

import static ru.vityaman.tidb.ui.printer.ConsoleColor.*;

public final class RequestCoordinates implements RequestInput<Coordinates> {
    private static final RequestInput<Double> X_REQUEST
        = new RequestPrimitive<>("x: ",
            (string) -> Coordinates.RequireValid.x(Double.parseDouble(string)),
            new HashSet<Class<? extends Exception>>() {{
                add(NumberFormatException.class);
                add(InvalidValueException.class);
            }});

    private static final RequestInput<Double> Y_REQUEST
            = new RequestPrimitive<>("y: ",
            (string) -> Coordinates.RequireValid.y(Double.parseDouble(string)),
            new HashSet<Class<? extends Exception>>() {{
                add(NumberFormatException.class);
                add(InvalidValueException.class);
            }});

    @Override
    public Coordinates from(Input in, Printer out) {
        CoordinatesBean coordinates = new CoordinatesBean();

        out.print("coordinates: " + BLUE.wrapped("{\n"));

        Printer prefixed = new Colored(BLUE, new Prefixed("    ", out));
        coordinates.setX(X_REQUEST.from(in, prefixed));
        coordinates.setY(Y_REQUEST.from(in, prefixed));

        out.print(BLUE.wrapped("}\n"));

        return coordinates.validated();
    }
}
