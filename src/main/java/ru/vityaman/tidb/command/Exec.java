package ru.vityaman.tidb.command;

import ru.vityaman.tidb.command.exception.RecursiveCallException;
import ru.vityaman.tidb.data.file.TextFile;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.Interpreter;
import ru.vityaman.tidb.lang.parse.Parse;

import java.util.List;


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

    @Override
    public void execute(List<Object> args) {
        String filepath = (String) args.get(0);
        TextFile file = new TextFile(filepath);
        String program = file.content();
        for (Instruction instruction : Parse.instructions(program)) {
            if (instruction.name().equals("exec")
                    && instruction.arguments().get(0).equals(filepath)) {
                throw new RecursiveCallException(filepath);
            }
            interpreter.execute(instruction);
        }
    }
}
