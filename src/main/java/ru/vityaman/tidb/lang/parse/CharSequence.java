package ru.vityaman.tidb.lang.parse;

public interface CharSequence {
    char next();
    boolean hasNext();
    ParsingException error(String message);
    CharSequence suffix();
    CharSequence prefix();
}
