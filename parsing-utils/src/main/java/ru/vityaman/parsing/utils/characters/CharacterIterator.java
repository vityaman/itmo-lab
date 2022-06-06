package ru.vityaman.parsing.utils.characters;

public interface CharacterIterator {
    /**
     * Moves to next char and returns it.
     * @return next char
     */
    char next();

    /**
     * @return true if it has next char.
     */
    boolean hasNext();
}
