package ru.vityaman.parsing.utils.characters;

import java.util.Arrays;

public final class ArrayCharacters extends AbstractCharacters {
    private final char[] chars;

    ArrayCharacters(char[] chars) {
        this.chars = Arrays.copyOf(chars, chars.length);
    }

    @Override
    public int size() {
        return chars.length;
    }

    @Override
    public CharacterIterator iterator() {
        return new CharacterIterator() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public char next() {
                return chars[i];
            }
        };
    }
}
