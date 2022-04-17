package ru.vityaman.tidb.lang.parse;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static ru.vityaman.tidb.lang.parse.CharTest.equalTo;

public final class ParsingSequence {
    private static final char DEFAULT_END = 0;
    private static final Set<Character> DEFAULT_WHITESPACES = new HashSet<Character>() {{
        add(' ');
        add('\t');
        add('\r');
        add('\n');
    }};

    private final char end;
    private final Set<Character> whitespaces;

    private final CharSequence sequence;
    private char current;

    public ParsingSequence(CharSequence sequence) {
        this(sequence, DEFAULT_END, DEFAULT_WHITESPACES);
    }

    public ParsingSequence(CharSequence sequence,
                           char end, Set<Character> whitespaces) {
        this.end = end;
        this.whitespaces = new TreeSet<>(whitespaces);

        this.sequence = sequence;
        current = (char) (end + 1);
        take();
    }

    public boolean current(CharTest expectation) {
        return expectation.test(current);
    }

    public char take() {
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

    public boolean take(CharTest expectation) {
        if (expectation.test(current)) {
            take();
            return true;
        }
        return false;
    }

    public void expect(CharTest expectation, String failureCause) {
        if (!take(expectation)) {
            throw error(String.format(
                    "Found '%s': '%s'", current, failureCause));
        }
    }

    public void expect(char expected) {
        expect(equalTo(expected), "not equal to " + expected);
    }

    public void expect(String expected) {
        for (char ch : expected.toCharArray()) {
            expect(ch);
        }
    }

    public void expectWhitespace() {
        expect(whitespaces::contains, "Not in " + whitespaces);
    }

    public void expectEnded() {
        if (!isEnded()) {
            throw error(String.format(
                    "Expected end of input, found '%s'", current));
        }
    }

    public void skipWhitespaces() {
        while (take(whitespaces::contains)) { /* Do nothing */ }
    }

    public boolean isEnded() {
        return current(equalTo(end));
    }

    public ParsingException error(String message) {
        return sequence.error(message);
    }
}
