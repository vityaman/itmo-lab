package ru.vityaman.parsing.utils.characters;

public abstract class AbstractCharacters implements Characters {

    @Override
    public boolean contains(char character) {
        CharacterIterator iterator = iterator();
        while (iterator.hasNext()) {
            if (character == iterator.next()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public char[] elements() {
        char[] result = new char[size()];

        int i = 0;
        CharacterIterator iterator = iterator();
        while (iterator.hasNext()) {
            result[i] = iterator.next();
            i++;
        }

        return result;
    }
}
