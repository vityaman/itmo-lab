package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.out.Out;


@FunctionalInterface
public interface RequestInput<T> {
    T from(Input in, Out out);
}
