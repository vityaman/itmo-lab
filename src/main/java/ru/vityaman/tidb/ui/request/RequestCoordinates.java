package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.data.dto.CoordinatesInfo;
import ru.vityaman.tidb.data.model.Coordinates;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Colored;
import ru.vityaman.tidb.ui.out.Prefixed;
import ru.vityaman.tidb.ui.out.Out;

import static ru.vityaman.tidb.ui.out.ConsoleColor.*;

import java.util.HashSet;

/**
 * Requests coordinates from user.
 */
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
    public Coordinates from(Input in, Out out) {
        CoordinatesInfo.Builder coordinates = CoordinatesInfo.builder();

        out.print("coordinates: " + BLUE.wrapped("{\n"));

        Out prefixed = new Colored(BLUE, new Prefixed("    ", out));
        coordinates.withX(X_REQUEST.from(in, prefixed))
                   .withY(Y_REQUEST.from(in, prefixed));

        out.print(BLUE.wrapped("}\n"));

        return coordinates.instance();
    }
}
