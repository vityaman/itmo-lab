package ru.vityaman.tidb.lang.parse;

import java.util.Iterator;

import ru.vityaman.tidb.lang.interpreter.Instruction;

public final class Program implements Iterable<Instruction> {
    private final Parser parser;

    public Program(CharSequence source) {
        parser = new Parser(source);
    }

    @Override
    public Iterator<Instruction> iterator() {
        return parser.parseProgram().iterator();
    }
}
