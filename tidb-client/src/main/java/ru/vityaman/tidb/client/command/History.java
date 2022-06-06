package ru.vityaman.tidb.client.command;

import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.Instruction;
import ru.vityaman.tidb.client.ui.out.Out;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Represents a 'history' command.
 */
public final class History implements Executable {
    private final Out out;
    private final Supplier<Instruction[]> history;

    /**
     * @param out where to print out.
     * @param history where to get history.
     */
    public History(Out out, Supplier<Instruction[]> history) {
        this.out = out;
        this.history = history;
    }

    private void execute() {
        Arrays.stream(history.get()).forEachOrdered((instruction -> {
            out.println("- " + instruction);
        }));
    }

    @Override
    public void execute(List<Object> args) {
        execute();
    }

}
