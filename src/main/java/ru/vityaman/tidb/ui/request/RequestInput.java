package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Out;


/**
 * Requests abstract datatype from user
 * <T> - type
 */
@FunctionalInterface
public interface RequestInput<T> {
    /**
     * @param in where to read input
     * @param out where to print hints
     * @return builded object
     */
    T from(Input in, Out out);
}
