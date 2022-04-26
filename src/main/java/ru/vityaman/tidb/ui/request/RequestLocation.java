package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.dto.LocationData;
import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Colored;
import ru.vityaman.tidb.ui.out.Prefixed;
import ru.vityaman.tidb.ui.out.Out;

import static ru.vityaman.tidb.ui.out.ConsoleColor.CYAN;

import java.util.HashSet;

/**
 * Requests a location from user.
 */
public final class RequestLocation implements RequestInput<Location> {
    private static final RequestInput<Float> X_REQUEST
        = new RequestPrimitive<>(
            "x: ",
            (string) -> Location.RequireValid.x(Float.parseFloat(string)),
            new HashSet<Class<? extends Exception>>() {{
                add(NumberFormatException.class);
                add(InvalidValueException.class);
            }});

    private static final RequestInput<Double> Y_REQUEST
            = new RequestPrimitive<>(
            "y: ",
            (string) -> Location.RequireValid.y(Double.parseDouble(string)),
            new HashSet<Class<? extends Exception>>() {{
                add(NumberFormatException.class);
                add(InvalidValueException.class);
            }});

    private static final RequestInput<Float> Z_REQUEST
            = new RequestPrimitive<>(
            "z: ",
            (string) -> Location.RequireValid.z(Float.parseFloat(string)),
            new HashSet<Class<? extends Exception>>() {{
                add(NumberFormatException.class);
                add(InvalidValueException.class);
            }});

    private static final RequestInput<String> NAME_REQUEST
            = new RequestPrimitive<>(
            "name: ",
            (string) -> Location.RequireValid.name(string),
            new HashSet<Class<? extends Exception>>() {{
                add(InvalidValueException.class);
            }});

    @Override
    public Location from(Input in, Out out) {
        LocationData.Builder location = LocationData.builder();

        out.print("location: " + CYAN.wrapped("{\n"));

        Out prefixed = new Colored(CYAN, new Prefixed("    ", out));
        location.withX(X_REQUEST.from(in, prefixed))
                .withY(Y_REQUEST.from(in, prefixed))
                .withZ(Z_REQUEST.from(in, prefixed))
                .withName(NAME_REQUEST.from(in, prefixed));

        out.print(CYAN.wrapped("}\n"));

        return location.instance();
    }
}
