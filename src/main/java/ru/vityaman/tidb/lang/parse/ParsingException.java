package ru.vityaman.tidb.lang.parse;

/**
 * Error while parsing.
 */
public class ParsingException extends RuntimeException {
    public ParsingException(String message) {
        super(message);
    }
}
