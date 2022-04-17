package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.data.bean.LocationBean;
import ru.vityaman.tidb.data.model.exception.InvalidValueException;
import ru.vityaman.tidb.data.model.Location;
import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Colored;
import ru.vityaman.tidb.ui.printer.Prefixed;
import ru.vityaman.tidb.ui.printer.Printer;

import java.util.HashSet;

import static ru.vityaman.tidb.ui.printer.ConsoleColor.CYAN;

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
    public Location from(Input in, Printer out) {
        LocationBean location = new LocationBean();

        out.print("location: " + CYAN.wrapped("{\n"));

        Printer prefixed = new Colored(CYAN, new Prefixed("    ", out));
        location.setX(X_REQUEST.from(in, prefixed));
        location.setY(Y_REQUEST.from(in, prefixed));
        location.setZ(Z_REQUEST.from(in, prefixed));
        location.setName(NAME_REQUEST.from(in, prefixed));

        out.print(CYAN.wrapped("}\n"));

        return location.validated();
    }
}
