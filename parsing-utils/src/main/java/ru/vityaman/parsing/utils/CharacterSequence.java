package ru.vityaman.parsing.utils;

import ru.vityaman.parsing.utils.characters.CharacterIterator;

/**
 * Source of characters.
 */
public interface CharacterSequence extends CharacterIterator {
    /**
     * @param message error message
     * @return exception to throw
     */
    ParsingException error(String message);
}
