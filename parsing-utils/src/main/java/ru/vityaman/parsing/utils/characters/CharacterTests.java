package ru.vityaman.parsing.utils.characters;

import java.util.Arrays;

public final class CharacterTests {
    
    public static CharacterTest notEqualTo(char other) {
        return new CharacterTest(
            (ch) -> ch != other,
            String.format("not equal to '%s'", other)
        );
    }

    private CharacterTests() {
        throw new AssertionError("Utility class");
    }

    public static CharacterTest equalTo(char other) {
        return new CharacterTest(
            ch -> ch == other,
            String.format("equal to '%s'", other)
        );
    }

    public static CharacterTest between(char min, char max) {
        return new CharacterTest(
            ch -> min <= ch && ch <= max,
            String.format("in ['%s','%s']", min, max)
        );
    }

    public static CharacterTest in(Characters characters) {
        return new CharacterTest(
            characters::contains,
            String.format(
                "in %s", 
                Arrays.toString(characters.elements())
            )
        );
    }

    public static CharacterTest in(char... chars) {
        return new CharacterTest(
            ch -> {
                for (char other : chars) {
                    if (other == ch) {
                        return true;
                    }
                }
                return false;
            }
        );
    }

    public static CharacterTest in(String chars) {
        return in(chars.toCharArray());
    }

    public static CharacterTest any(CharacterTest... expectations) {
        return new CharacterTest(
            ch -> {
                for (CharacterTest expectation : expectations) {
                    if (expectation.isValid(ch)) {
                        return true;
                    }
                }
                return false;
            }
        );
    }
}
