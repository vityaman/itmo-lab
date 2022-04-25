package ru.vityaman.tidb.command;

import java.util.List;

import ru.vityaman.tidb.command.exception.RecursiveCallException;
import ru.vityaman.tidb.data.file.TextFile;
import ru.vityaman.tidb.lang.interpreter.Executable;
import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.interpreter.Interpreter;
import ru.vityaman.tidb.lang.parse.Parse;


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

    public void execute(String scriptpath) {
        TextFile file = new TextFile(new java.io.File(scriptpath));
        String program = file.content();
        for (Instruction instruction : Parse.instructions(program)) {
            if (instruction.name().equals("exec")
                    && instruction.arguments().get(0).equals(scriptpath)) {
                throw new RecursiveCallException(scriptpath);
            }
            interpreter.execute(instruction);
        }
    }

    @Override
    public void execute(List<Object> args) {
        execute((String) args.get(0));
    }
}
