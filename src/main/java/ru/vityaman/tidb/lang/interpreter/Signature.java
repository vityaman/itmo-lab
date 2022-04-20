package ru.vityaman.tidb.lang.interpreter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Signature {
    private final String name;
    private final Class<?>[] argumentTypes;

    public Signature(String name, Class<?>... argumentTypes) {
        this.name = name;
        this.argumentTypes = argumentTypes;
    }

    public Signature(String name) {
        this(name, new Class[0]);
    }

    public String name() {
        return name;
    }

    public Class<?>[] argumentTypes() {
        return argumentTypes;
    }

    public boolean fitsTo(Signature other) {
        if (argumentTypes.length != other.argumentTypes.length
                || !name.equals(other.name)) {
            return false;
        }

        for (int i = 0; i < argumentTypes.length; i++) {
            if (!other.argumentTypes[i]
                    .isAssignableFrom(argumentTypes[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature other = (Signature) o;
        return name.equals(other.name)
                && Arrays.equals(argumentTypes, other.argumentTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, Arrays.hashCode(argumentTypes));
    }

    @Override
    public String toString() {
        return String.format("%s(%s\b)",
                name, Arrays.stream(argumentTypes)
                            .map(Class::getName)
                            .collect(Collectors.toList())
                            .toString().substring(1));
    }
}