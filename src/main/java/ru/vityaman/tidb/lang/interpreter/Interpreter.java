package ru.vityaman.tidb.lang.interpreter;

import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.lang.interpreter.exception.InterpreterException;

public interface Interpreter {
    void execute(Instruction instruction) throws InterpreterException,
                                                 ExecutionException;
}
