package ru.vityaman.parsing.utils;

import static ru.vityaman.parsing.utils.characters.CharacterTests.equalTo;
import static ru.vityaman.parsing.utils.characters.CharacterTests.in;

import ru.vityaman.parsing.utils.characters.CharacterTest;
import ru.vityaman.parsing.utils.characters.Characters;

/**
 *
 */
public final class ParsingSequence {
    private static final char DEFAULT_END = 0;
    private static final Characters DEFAULT_WHITESPACES = 
        Characters.of(' ', '\t', '\r', '\n');

    private final char end;
    private final Characters whitespaces;

    private final CharacterSequence sequence;
    private char current;

    public ParsingSequence(CharacterSequence sequence) throws ParsingException {
        this(sequence, DEFAULT_END, DEFAULT_WHITESPACES);
    }

    public ParsingSequence(CharacterSequence sequence,
                           char end, Characters whitespaces) throws ParsingException {
        this.end = end;
        this.whitespaces = whitespaces;
        this.sequence = sequence;
        current = (char) (end + 1);
        take();
    }

    public boolean isCurrent(CharacterTest test) {
        return test.isValid(current);
    }

    public char take() throws ParsingException {
        if (isEnded()) {
            throw error("Expected char, found end of sequence");
        }
        char taken = this.current;
        if (sequence.hasNext()) {
            this.current = sequence.next();
        } else {
            this.current = end;
        }
        return taken;
    }

    public boolean take(CharacterTest test) throws ParsingException {
        if (test.isValid(current)) {
            take();
            return true;
        }
        return false;
    }

    public void expect(String expected) throws ParsingException {
        for (char ch : expected.toCharArray()) {
            expect(ch);
        }
    }

    public void expect(char expected) throws ParsingException {
        expect(equalTo(expected));
    }

    public void expect(CharacterTest expectation) throws ParsingException {
        if (!take(expectation)) {
            throw error(String.format(
                "Found '%s', but expected %s", 
                current, expectation.description()
            ));
        }
    }

    public void expectWhitespace() throws ParsingException {
        expect(in(whitespaces));
    }

    public void expectEnded() throws ParsingException {
        if (!isEnded()) {
            throw error(String.format(
                "Expected end of input, found '%s'", 
                current
            ));
        }
    }

    public void skipWhitespaces() throws ParsingException {
        while (take(in(whitespaces))) { /* Do nothing */ }
    }

    public boolean isEnded() {
        return isCurrent(equalTo(end));
    }

    public ParsingException error(String message) {
        return sequence.error(message);
    }
}
