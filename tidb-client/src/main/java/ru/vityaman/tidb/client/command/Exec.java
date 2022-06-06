package ru.vityaman.tidb.client.command;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ru.vityaman.parsing.utils.LineByLineSequence;
import ru.vityaman.parsing.utils.ParsingException;
import ru.vityaman.tidb.client.file.FileLines;
import ru.vityaman.tidb.client.lang.interpreter.Executable;
import ru.vityaman.tidb.client.lang.interpreter.Instruction;
import ru.vityaman.tidb.client.lang.interpreter.Interpreter;
import ru.vityaman.tidb.client.lang.interpreter.exception.ExecutionException;
import ru.vityaman.tidb.client.lang.interpreter.exception.InterpreterException;
import ru.vityaman.tidb.client.lang.Program;
import ru.vityaman.tidb.client.ui.out.Out;


/**
 * Represents a command 'exec' that
 * executes instructions from file.
 */
public final class Exec implements Executable {
    private final Interpreter interpreter;
    private Out out;

    /**
     * @param interpreter who executes commands
     */
    public Exec(Out out, Interpreter interpreter) {
        this.out = out;
        this.interpreter = interpreter;
    }

    public void execute(String scriptpath) throws ExecutionException {
        Path path = Paths.get(scriptpath);
        try (FileLines lines = new FileLines(path)) {
            Program program = new Program(new LineByLineSequence(lines));
            for (Instruction instruction : program) {
                try {
                    interpreter.execute(instruction);
                } catch (InterpreterException e) {
                    out.error(path + ": runtime error: " + e.getMessage());
                }
            }
        } catch (IOException | ParsingException e) {
            throw new ExecutionException(e);
        } 
    }

    @Override
    public void execute(List<Object> args) throws ExecutionException {
        execute((String) args.get(0));
    }
}
