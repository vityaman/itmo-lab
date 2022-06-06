package ru.vityaman.tidb.client.lang.interpreter;

import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.lang.interpreter.exception.InterpreterException;

/**
 * Can execute instructions.
 */
public interface Interpreter {
    void execute(Instruction instruction) 
    throws InterpreterException, ExecutionException;
}
