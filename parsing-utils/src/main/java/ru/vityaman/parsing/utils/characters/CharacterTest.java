package ru.vityaman.parsing.utils.characters;


public final class CharacterTest {
    private final Predicate test;
    private final String description;

    public CharacterTest(Predicate test, String description) {
        this.test = test;
        this.description = description;
    }

    public CharacterTest(Predicate test) {
        this(test, "Invalid character");
    }

    public boolean isValid(char character) {
        return test.isValid(character);
    }

    public String description() { 
        return description; 
    }

    @FunctionalInterface
    public interface Predicate {
        /**
        * @param character char to test.
        * @return true if character is valid.
        */
        boolean isValid(char character);
    }
}
