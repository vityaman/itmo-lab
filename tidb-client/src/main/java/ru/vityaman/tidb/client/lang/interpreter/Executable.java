package ru.vityaman.tidb.client.lang.interpreter;

import java.util.List;

import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;

/**
 * Represents a something that
 * can be executed.
 */
public interface Executable {
    /**
     * Call this method to execute task.
     * @param args list of arguments.
     */
    void execute(List<Object> args) throws ExecutionException;
}
