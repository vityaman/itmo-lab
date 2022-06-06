package ru.vityaman.parsing.utils;

public final class StringSequence implements CharacterSequence {
    private final String string;
    private int currentPosition;

    public StringSequence(String string) {
        this.string = string;
        this.currentPosition = 0;
    }

    @Override
    public char next() {
        return string.charAt(currentPosition++);
    }

    @Override
    public boolean hasNext() {
        return currentPosition < string.length();
    }

    @Override
    public ParsingException error(String message) {
        return new ParsingException(String.format(
            "Parsing error on position %d: %s (%s)",
            currentPosition, message,
            string.substring(
                Math.max(currentPosition - 10, 0),
                Math.min(currentPosition + 10, string.length())
            )
        ));
    }
}
