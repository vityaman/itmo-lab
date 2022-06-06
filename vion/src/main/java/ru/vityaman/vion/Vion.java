package ru.vityaman.vion;

import ru.vityaman.parsing.utils.ParsingException;
import ru.vityaman.parsing.utils.StringSequence;

/**
 * Utility class for parsing.
 */
public final class Vion {
    public static VionObject parseObject(String string) throws ParsingException {
        VionParser vionParser = parserFor(string);
        VionObject result = vionParser.parseObject();
        vionParser.expectEnd();
        return result;
    }

    public static String parseString(String string) throws  ParsingException {
        VionParser vionParser = parserFor(string);
        String result = vionParser.parseString();
        vionParser.expectEnd();
        return result;
    }

    private static VionParser parserFor(String string) throws ParsingException {
        return new VionParser(new StringSequence(string));
    }

    private Vion() { throw new AssertionError("Utility class"); }
}
