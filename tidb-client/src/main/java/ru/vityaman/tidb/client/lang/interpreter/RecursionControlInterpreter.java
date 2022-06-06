package ru.vityaman.tidb.client.lang.interpreter;

import java.util.ArrayDeque;
import java.util.Deque;

import ru.vityaman.tidb.client.lang.interpreter.exception.InterpreterException;
import ru.vityaman.tidb.client.lang.interpreter.exception.RecursiveCallException;

public final class RecursionControlInterpreter implements Interpreter {
    private final Interpreter origin;
    private final Deque<Instruction> callStack = new ArrayDeque<>();
     
    public RecursionControlInterpreter(Interpreter origin) {
        this.origin = origin;
    }
    
    @Override
    public void execute(Instruction instruction) 
    throws InterpreterException {
        if (callStack.contains(instruction)) {
            throw new RecursiveCallException(
                instruction, 
                callStack
            );
        }
        callStack.addLast(instruction);
        try {
            origin.execute(instruction);   
        } catch (InterpreterException e) {
            throw new InterpreterException(e);
        } finally {
            callStack.removeLast();
        }
    }
}
