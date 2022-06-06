package ru.vityaman.tidb.client.lang;

import java.util.Iterator;

import ru.vityaman.parsing.utils.CharacterSequence;
import ru.vityaman.parsing.utils.ParsingException;
import ru.vityaman.parsing.utils.StringSequence;
import ru.vityaman.tidb.client.lang.interpreter.Instruction;

public final class Program implements Iterable<Instruction> {
    private final ProgramParser programParser;

    public Program(CharacterSequence source) throws ParsingException {
        programParser = new ProgramParser(source);
    }

    @Override
    public Iterator<Instruction> iterator() {
        return programParser.parseProgram().iterator();
    }

    public static Instruction parseInstruction(String string)
            throws ParsingException {
        ProgramParser parser = new ProgramParser(new StringSequence(string));
        Instruction result = parser.parseInstruction();
        parser.expectEnd();
        return result;
    }
}
