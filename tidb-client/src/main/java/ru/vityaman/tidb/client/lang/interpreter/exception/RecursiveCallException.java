package ru.vityaman.tidb.client.lang.interpreter.exception;

import java.util.Collection;

import ru.vityaman.tidb.client.lang.interpreter.Instruction;

/**
 * Error: script is called recursively.
 */
public class RecursiveCallException extends ExecutionException {
    public RecursiveCallException(
        Instruction instruction,
        Collection<Instruction> callStackFrame
    ) {
        super(String.format(
            "Recursive call of instruction %s, callStack: %s",
            instruction, callStackFrame
        ));
    }
}
