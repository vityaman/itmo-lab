package ru.vityaman.tidb.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ru.vityaman.tidb.data.file.FileLines;
import ru.vityaman.tidb.data.file.exception.FileSystemException;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.Interpreter;
import ru.vityaman.tidb.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.lang.interpreter.exception.InterpreterException;
import ru.vityaman.tidb.lang.interpreter.exception.RecursiveCallException;
import ru.vityaman.tidb.lang.parse.LinesSequence;
import ru.vityaman.tidb.lang.parse.Program;


/**
 * Represents a command 'exec' that
 * executes instructions from file.
 */
public final class Exec implements Executable {
    private final Interpreter interpreter;

    /**
     * @param interpreter who executes commands
     */
    public Exec(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void execute(String scriptpath) throws ExecutionException {
        Path path = Paths.get(scriptpath);
        try (FileLines lines = new FileLines(path)) {
            Program program = new Program(new LinesSequence(lines));
            for (Instruction instruction : program) {
                if (isRecusiveCall(instruction, scriptpath)) {
                    throw new RecursiveCallException(scriptpath);
                }
                interpreter.execute(instruction);
            }
        } catch (FileSystemException | InterpreterException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        execute((String) args.get(0));
    }

    private static boolean isRecusiveCall(
        Instruction instruction, String scriptpath
    ) {
        return instruction.name().equals("exec")
            && !instruction.arguments().isEmpty()
            && instruction.arguments().get(0).equals(scriptpath);
    }
}
