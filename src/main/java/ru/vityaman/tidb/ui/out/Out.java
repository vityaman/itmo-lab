package ru.vityaman.tidb.ui.out;

import static ru.vityaman.tidb.ui.out.ConsoleColor.RED;

public interface Out {
    Out print(String text);

    default Out print(String... texts) {
        for (String text : texts) {
            print(text);
        }
        return this;
    }

    default Out println(String text) {
        return print(text + '\n');
    }

    default Out println(String... texts) {
        for (String text : texts) {
            print(text);
        }
        return print("\n");
    }

    default Out error(String text) {
        return println(RED.wrapped(text));
    }

    default Out prefixed(String prefix) {
        return new Prefixed(prefix, this);
    }

    default Out colored(ConsoleColor color) {
        return new Colored(color, this);
    }
}
