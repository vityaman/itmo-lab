package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Printer;

import java.util.Set;
import java.util.function.Function;

public final class RequestPrimitive<T> implements RequestInput<T> {
        private final String prefix;
        private final Function<String, T> cast;
        private final Set<Class<? extends Exception>> catchables;

        public RequestPrimitive(
                String prefix, Function<String, T> cast,
                Set<Class<? extends Exception>> catchables) {
            this.prefix = prefix;
            this.cast = cast;
            this.catchables = catchables;
        }

    @Override
    public T from(Input in, Printer out) {
        while (true) {
            try {
                out.print(prefix);
                String input = in.readLine();
                return cast.apply(input);
            } catch (Exception e) {
                if (catchables.contains(e.getClass())) {
                    out.error(String.format(
                                    "Invalid value: %s. Enter it again...",
                                    e.getMessage()));
                } else {
                    throw e;
                }
            }
        }
    }
}
