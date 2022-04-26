package ru.vityaman.tidb.lang.parse;

import ru.vityaman.tidb.lang.interpreter.Instruction;

/**
 * Utility class for parsing.
 */
public final class Parse {
    /**
     * @param string where to parse instruction.
     * @return parsed instruction.
     */
    public static Instruction instruction(String string) {
        Parser parser = new Parser(new StringSequence(string));
        Instruction instruction = parser.parseInstruction();
        parser.expectEnd();
        return instruction;
    }

    /**
     * Consistently parses instructions from string one by one.
     * @param string where to parse program.
     * @return instructions
     */
    public static Iterable<Instruction> instructions(String string) {
        return new Parser(new StringSequence(string)).parseProgram();
    }

    private Parse() { throw new AssertionError("Utility class"); }
}
