package ru.vityaman.tidb.client.lang;

import ru.vityaman.parsing.utils.CharacterSequence;
import ru.vityaman.parsing.utils.ParsingException;
import ru.vityaman.parsing.utils.ParsingSequence;
import ru.vityaman.parsing.utils.UncheckedParsingException;
import ru.vityaman.tidb.client.lang.interpreter.Instruction;
import ru.vityaman.vion.VionParser;

import java.util.*;
import static ru.vityaman.parsing.utils.characters.CharacterTests.*;

/**
 * Parses char source.
 */
final class ProgramParser {
    private final ParsingSequence sequence;
    private final VionParser vionParser;

    /**
     * @param source source of characters.
     */
    ProgramParser(CharacterSequence source) throws ParsingException {
        sequence = new ParsingSequence(source);
        vionParser = new VionParser(source);
    }

    void expectEnd() throws ParsingException {
        sequence.skipWhitespaces();
        sequence.expectEnded();
    }

    Iterable<Instruction> parseProgram() {
        return () -> new Iterator<Instruction>() {
            @Override
            public boolean hasNext() {
                return !sequence.isEnded();
            }

            @Override
            public Instruction next() {
                try {
                    return parseInstruction();
                } catch (ParsingException e) {
                    throw new UncheckedParsingException(e);
                }
            }
        };
    }

    Instruction parseInstruction() throws ParsingException {
        sequence.skipWhitespaces();
        String name = parseChars();
        sequence.expectWhitespace();
        sequence.skipWhitespaces();
        if (sequence.isEnded() || sequence.isCurrent(any(
                between('a', 'z'),
                between('A', 'Z'),
                equalTo('_')))) {
            return new Instruction(name, new ArrayList<>());
        }
        List<Object> arguments = vionParser.parseValues();
        sequence.skipWhitespaces();
        return new Instruction(name, arguments);
    }

    String parseChars() throws ParsingException {
        StringBuilder result = new StringBuilder();
        while (sequence.isCurrent(any(
                between('A', 'Z'), between('a', 'z'),
                between('0', '9'),
                equalTo('_')))) {
            result.append(sequence.take());
        }
        return result.toString();
    }
}
