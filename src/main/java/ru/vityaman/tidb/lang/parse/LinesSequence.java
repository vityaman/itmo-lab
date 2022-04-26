package ru.vityaman.tidb.lang.parse;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class LinesSequence implements CharSequence {
    private final Iterator<String> lines;

    private int row;
    private int column;
    private CharSequence currentLine;

    public LinesSequence(Iterable<String> lines) {
        this.lines = lines.iterator();
        this.row = 0;
    }

    @Override
    public char next() {
        if (hasNext()) {
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
            currentLine = new StringSequence(lines.next() + '\n');
            return hasNext();
        }
        return false;
    }

    @Override
    public ParsingException error(String message) {
        return new ParsingException(String.format(
                "Parsing error on position (%d, %d): %s",
                row, column, message
            )
        );
    }
}
