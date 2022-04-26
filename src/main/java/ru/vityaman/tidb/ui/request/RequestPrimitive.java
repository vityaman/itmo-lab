package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Out;

import java.util.Set;
import java.util.function.Function;

/**
 * Requests primitive value from user.
 */
public final class RequestPrimitive<T> implements RequestInput<T> {
    private final String prefix;
    private final Function<String, T> cast;
    private final Set<Class<? extends Exception>> catchables;

    /**
     * @param prefix prefix of input
     * @param cast f: String -> T for casting
     * @param catchables excpetions to handle
     */
    public RequestPrimitive(
            String prefix, Function<String, T> cast,
            Set<Class<? extends Exception>> catchables) {
        this.prefix = prefix;
        this.cast = cast;
        this.catchables = catchables;
    }

    @Override
    public T from(Input in, Out out) {
        while (true) {
            try {
                out.print(prefix);
                String input = in.readLine().trim();
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
