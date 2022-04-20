package ru.vityaman.tidb.command;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.ui.printer.Printer;

/**
 * Represents a 'history' command.
 */
public final class History implements Executable {
    private final Printer out;
    private final Supplier<Instruction[]> history;

    /**
     * @param out where to print out.
     * @param history where to get history.
     */
    public History(Printer out, Supplier<Instruction[]> history) {
        this.out = out;
        this.history = history;
    }

    @Override
    public void execute(List<Object> args) {
        Arrays.stream(history.get()).forEachOrdered((instruction -> {
            out.println("- ", instruction.toString());
        }));
    }
}
