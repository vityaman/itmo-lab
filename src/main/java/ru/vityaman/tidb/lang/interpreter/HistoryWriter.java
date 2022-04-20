package ru.vityaman.tidb.lang.interpreter;

public class HistoryWriter implements Interpreter {
    private final Interpreter origin;
    private LimitedQueue<Instruction> history;

    public HistoryWriter(Interpreter origin, LimitedQueue<Instruction> history) {
        this.origin = origin;
        this.history = history;
    }

    @Override
    public void execute(Instruction instruction) {
        origin.execute(instruction);
        history.add(instruction);
    }

    public Instruction[] lastExecuted() {
        return history.elements();
    }
}
