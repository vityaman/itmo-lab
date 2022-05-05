package ru.vityaman.tidb.lang.parse;

import ru.vityaman.tidb.lang.interpreter.Instruction;
import ru.vityaman.tidb.lang.json.JsonObject;
import ru.vityaman.tidb.lang.json.MapJsonObject;

import java.util.*;

import static ru.vityaman.tidb.lang.parse.CharTest.*;

/**
 * Parses char source.
 */
final class Parser {
    private final ParsingSequence sequence;

    /**
     * @param source source of characters.
     */
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
        sequence.skipWhitespaces();
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

    JsonObject parseObject() {
        sequence.expect('{');
        sequence.skipWhitespaces();
        if (sequence.take(equalTo('}'))) {
            return new MapJsonObject();
        }
        JsonObject result = new MapJsonObject();
        Map.Entry<String, Object> member = parseMember();
        put(result, member);
        while (sequence.take(equalTo(','))) {
            member = parseMember();
            if (result.contains(member.getKey())) {
                throw sequence.error(
                        String.format("Duplicate key: '%s'", member.getKey()));
            }
            put(result, member);
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

    private void put(JsonObject json, Map.Entry<String, Object> member) {
        String key = member.getKey();
        Object value = member.getValue();
        if (value instanceof String) {
            json.put(key, (String) value);
        } else if (value instanceof Number) {
            json.put(key, (Number) value);
        } else if (value instanceof JsonObject) {
            json.put(key, (JsonObject) value);
        } else {
            throw new IllegalArgumentException(String.format(
                "Type %s is not supported",
                value.getClass()
            ));
        }
    }
}
