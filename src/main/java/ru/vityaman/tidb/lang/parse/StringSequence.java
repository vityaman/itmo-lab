package ru.vityaman.tidb.lang.parse;

public final class StringSequence implements CharSequence {
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

    public CharSequence suffix() {
        return new SubSequence(currentPosition, string.length());
    }

    public CharSequence prefix() {
        return new SubSequence(0, currentPosition);
    }

    @Override
    public ParsingException error(String message) {
        return new ParsingException(String.format(
                "Parsing error on position %d: %s (%s)",
                currentPosition, message,
                string.substring(
                        Math.max(currentPosition - 10, 0),
                        Math.min(currentPosition + 10, string.length()))
        ));
    }

    private final class SubSequence implements CharSequence {
        private final int start;
        private final int end;
        private int currentPosition;

        public SubSequence(int start, int end) {
            this.start = start;
            this.end = end;
            currentPosition = start;
        }

        @Override public char next() {
            return StringSequence.this.string.charAt(currentPosition++);
        }

        @Override public boolean hasNext() {
            return currentPosition < end;
        }

        public CharSequence suffix() {
            return new SubSequence(currentPosition, end);
        }

        public CharSequence prefix() {
            return new SubSequence(start, currentPosition);
        }

        @Override public ParsingException error(String message) {
            return StringSequence.this.error(message);
        }
    }
}
