package ru.vityaman.parsing.utils.characters;

public interface Characters {
    int size();
    boolean contains(char character);
    CharacterIterator iterator();
    char[] elements();

    static Characters of(char... characters) {
        return new ArrayCharacters(characters);
    }
}
