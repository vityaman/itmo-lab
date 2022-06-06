package ru.vityaman.tidb.client.lang.interpreter;


import ru.vityaman.tidb.client.lang.interpreter.exception.InterpreterException;

public final class HistoryWriter implements Interpreter {
    private final Interpreter origin;
    private ExecuteHistory history;

    public HistoryWriter(Interpreter origin, ExecuteHistory history) {
        this.origin = origin;
        this.history = history;
    }

    @Override
    public void execute(Instruction instruction) throws InterpreterException {
        origin.execute(instruction);
        history.add(instruction);
    }

    public Instruction[] lastExecuted() {
        return history.lastNInstructions();
    }
}
