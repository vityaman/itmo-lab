package ru.vityaman.tidb.lang.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Instruction {
    private final String name;
    private final List<Object> arguments;

    public Instruction(String name, List<Object> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public Instruction(String name) {
        this(name, new ArrayList<>());
    }

    public String name() {
        return name;
    }

    public List<Object> arguments() {
        return arguments;
    }

    public Signature signature() {
        return new Signature(name,
                arguments.stream()
                        .map(Object::getClass)
                        .toArray(Class[]::new));
    }

    @Override
    public String toString() {
        return name + '(' + arguments.toString().substring(1) + "\b)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return name.equals(that.name) && arguments.equals(that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arguments);
    }
}
