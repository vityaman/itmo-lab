package ru.vityaman.tidb.lang.parse;

import java.util.Set;

@FunctionalInterface
public interface CharTest {
    boolean test(char character);

    static CharTest notEqualTo(char other) {
        return (ch) -> ch != other;
    }

    static CharTest equalTo(char other) {
        return (ch) -> ch == other;
    }

    static CharTest between(char min, char max) {
        return (ch) -> min <= ch && ch <= max;
    }

    static CharTest in(Set<Character> characters) {
        return characters::contains;
    }

    static CharTest in(char... chars) {
        return (ch) -> {
            for (char other : chars) {
                if (other == ch) {
                    return true;
                }
            }
            return false;
        };
    }

    static CharTest in(String chars) {
        return in(chars.toCharArray());
    }

    static CharTest any(CharTest... expectations) {
        return (ch) -> {
            for (CharTest expectation : expectations) {
                if (expectation.test(ch)) {
                    return true;
                }
            }
            return false;
        };
    }
}
