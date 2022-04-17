package ru.vityaman.tidb.ui.request;

import ru.vityaman.tidb.ui.input.Input;
import ru.vityaman.tidb.ui.printer.Printer;


@FunctionalInterface
public interface RequestInput<T> {
    T from(Input in, Printer out);
}
