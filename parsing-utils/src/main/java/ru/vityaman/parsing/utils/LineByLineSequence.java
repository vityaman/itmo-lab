package ru.vityaman.parsing.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class LineByLineSequence implements CharacterSequence {
    private final Iterator<String> lines;
    private int row;
    private int column;
    private CharacterSequence currentLine;

    public LineByLineSequence(Iterable<String> lines) {
        this.lines = lines.iterator();
        this.row = 0;
    }

    @Override
    public char next() {
        if (hasNext()) {
            column++;
            return currentLine.next();
        }
        throw new NoSuchElementException(
            "No more charater in lines"
        );
    }

    @Override
    public boolean hasNext() {
        if (currentLine != null && currentLine.hasNext()) {
            return true;
        }
        if (lines.hasNext()) {
            row++;
            currentLine = new StringSequence(lines.next() + '\n');
            column = 0;
            return hasNext();
        }
        return false;
    }

    @Override
    public ParsingException error(String message) {
        return new ParsingException(String.format(
            "Parsing error on position (%s, %s): %s",
            row, column, message
        ));
    }
}
