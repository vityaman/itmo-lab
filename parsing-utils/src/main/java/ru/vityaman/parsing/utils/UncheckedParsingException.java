package ru.vityaman.parsing.utils;

public class UncheckedParsingException extends RuntimeException {
    public UncheckedParsingException(ParsingException e) {
        super(e.getMessage(), e);
    }
}
