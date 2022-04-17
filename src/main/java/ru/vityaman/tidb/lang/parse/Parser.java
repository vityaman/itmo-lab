package ru.vityaman.tidb.lang.parse;

import ru.vityaman.tidb.lang.interpreter.Instruction;

import java.util.*;

import static ru.vityaman.tidb.lang.parse.CharTest.*;

final class Parser {
    private final ParsingSequence sequence;

    Parser(CharSequence source) {
        sequence = new ParsingSequence(source);
    }

    void expectEnd() {
        sequence.skipWhitespaces();
        sequence.expectEnded();
    }

    Iterable<Instruction> parseProgram() {
        return new Iterable<Instruction>() {
            @Override
            public Iterator<Instruction> iterator() {
                return new Iterator<Instruction>() {
                    @Override
                    public boolean hasNext() {
                        return !sequence.isEnded();
                    }

                    @Override
                    public Instruction next() {
                        return parseInstruction();
                    }
                };
            }
        };
    }

    Instruction parseInstruction() {
        String name = parseChars();
        sequence.expectWhitespace();
        sequence.skipWhitespaces();
        if (sequence.isEnded() || sequence.current(any(
                between('a', 'z'),
                between('A', 'Z'),
                equalTo('_')))) {
            return new Instruction(name, new ArrayList<>());
        }
        List<Object> arguments = parseValues();
        sequence.skipWhitespaces();
        return new Instruction(name, arguments);
    }

    Object parseValue() {
        if (sequence.current(in("tf"))) {
            return parseBool();
        }
        if (sequence.current(in("-0123456789"))) {
            return parseNumber();
        }
        if (sequence.current(equalTo('"'))) {
            return parseString();
        }
        if (sequence.current(equalTo('['))) {
            return parseList();
        }
        if (sequence.current(equalTo('{'))) {
            return parseObject();
        }
        throw sequence.error("Value expected");
    }

    Map<String, Object> parseObject() {
        sequence.expect('{');
        sequence.skipWhitespaces();
        if (sequence.take(equalTo('}'))) {
            return new HashMap<>();
        }

        Map<String, Object> result = new HashMap<>();

        Map.Entry<String, Object> member = parseMember();
        result.put(member.getKey(), member.getValue());

        while (sequence.take(equalTo(','))) {
            member = parseMember();
            if (result.containsKey(member.getKey())) {
                throw sequence.error(
                        String.format("Duplicate key: '%s'", member.getKey()));
            }
            result.put(member.getKey(), member.getValue());
        }
        sequence.expect('}');
        return result;
    }

    String parseChars() {
        StringBuilder result = new StringBuilder();
        while (sequence.current(any(
                between('A', 'Z'), between('a', 'z'),
                between('0', '9'),
                equalTo('_')))) {
            result.append(sequence.take());
        }
        return result.toString();
    }

    Map.Entry<String, Object> parseMember() {
        sequence.skipWhitespaces();
        String key = parseChars();
        sequence.skipWhitespaces();

        sequence.expect(':');

        sequence.skipWhitespaces();
        Object value = parseValue();
        sequence.skipWhitespaces();

        return new Map.Entry<String, Object>() {
            @Override public String getKey() { return key; }

            @Override public Object getValue() { return value; }

            @Override public Object setValue(Object o) { return null; }
        };
    }

    List<Object> parseList() {
        sequence.expect('[');
        sequence.skipWhitespaces();
        if (sequence.take(equalTo(']'))) {
            return new ArrayList<>();
        }

        List<Object> result = parseValues();

        sequence.expect(']');

        return result;
    }

    List<Object> parseValues() {
        List<Object> result = new ArrayList<>();
        sequence.skipWhitespaces();
        result.add(parseValue());
        sequence.skipWhitespaces();
        while (sequence.take(equalTo(','))) {
            sequence.skipWhitespaces();
            result.add(parseValue());
            sequence.skipWhitespaces();
        }

        return result;
    }

    Boolean parseBool() {
        if (sequence.current(equalTo('f'))) {
            sequence.expect("false");
            return Boolean.FALSE;
        }
        sequence.expect("true");
        return Boolean.TRUE;
    }

    Number parseNumber() {
        StringBuilder number = new StringBuilder();
        if (sequence.take(equalTo('-'))) {
            number.append('-');
        }
        while (sequence.current(in("0123456789"))) {
            number.append(sequence.take());
        }
        if (sequence.take(equalTo('.'))) {
            number.append('.');
            while (sequence.current(in("0123456789"))) {
                number.append(sequence.take());
            }
        }

        String result = number.toString();
        try { return Integer.valueOf(result); } catch (NumberFormatException ignored) {}
        try { return Long.valueOf(result); } catch (NumberFormatException ignored) {}
        try { return Float.valueOf(result); } catch (NumberFormatException ignored) {}
        try { return Double.valueOf(result); } catch (NumberFormatException ignored) {}

        throw sequence.error("Invalid number");
    }

    String parseString() {
        sequence.expect('"');
        StringBuilder result = new StringBuilder();
        while (!sequence.take(equalTo('"'))) {
            result.append(sequence.take());
        }
        return result.toString();
    }
}
