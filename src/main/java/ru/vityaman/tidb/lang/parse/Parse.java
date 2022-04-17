package ru.vityaman.tidb.lang.parse;

import ru.vityaman.tidb.lang.interpreter.Instruction;

public final class Parse {
    public static Instruction instruction(String string) {
        Parser parser = new Parser(new StringSequence(string));
        Instruction instruction = parser.parseInstruction();
        parser.expectEnd();
        return instruction;
    }

    public static Iterable<Instruction> instructions(String string) {
        return new Parser(new StringSequence(string)).parseProgram();
    }
}
