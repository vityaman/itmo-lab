package ru.vityaman.tidb.lang.parse;

/**
 * Source of characters.
 */
public interface CharSequence {
    /**
     * Moves to next char and returns it.
     * @return next char
     */
    char next();

    /**
     * @return true if it has next char.
     */
    boolean hasNext();

    /**
     * @param message error message
     * @return exception to throw
     */
    ParsingException error(String message);
}
