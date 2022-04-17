package ru.vityaman.tidb.lang.interpreter;

import java.util.LinkedList;
import java.util.Queue;

public class HistoryWriter implements Interpreter {
    private final Interpreter origin;
    private final Queue<Instruction> history;
    private final int historySize;

    public HistoryWriter(Interpreter origin, int historySize) {
        this.origin = origin;
        this.history = new LinkedList<>();
        this.historySize = historySize;
    }

    @Override
    public void execute(Instruction instruction) {
        origin.execute(instruction);
        if (history.size() >= historySize) {
            history.poll();
        }
        history.add(instruction);
    }

    public Instruction[] lastExecutedInstructions() {
        return history.toArray(new Instruction[0]);
    }
}
