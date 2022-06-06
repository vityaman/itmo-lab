package ru.vityaman.vion;

import java.util.*;

import ru.vityaman.parsing.utils.CharacterSequence;
import ru.vityaman.parsing.utils.ParsingException;
import ru.vityaman.parsing.utils.ParsingSequence;
import static ru.vityaman.parsing.utils.characters.CharacterTests.*;

/**
 * Parses char source.
 */
public final class VionParser {
    private final ParsingSequence sequence;

    /**
     * @param source source of characters.
     * @throws ParsingException
     */
    public VionParser(CharacterSequence source) throws ParsingException {
        this.sequence = new ParsingSequence(source);
    }

    void expectEnd() throws ParsingException {
        sequence.skipWhitespaces();
        sequence.expectEnded();
    }

    public Object parseValue() throws ParsingException {
        if (sequence.isCurrent(in("-0123456789"))) {
            return parseNumber();
        }
        if (sequence.isCurrent(equalTo('"'))) {
            return parseString();
        }
        if (sequence.isCurrent(equalTo('['))) {
            return parseList();
        }
        if (sequence.isCurrent(equalTo('{'))) {
            return parseObject();
        }
        throw sequence.error("Value expected");
    }

    public VionObject parseObject() throws ParsingException {
        sequence.expect('{');
        sequence.skipWhitespaces();
        if (sequence.take(equalTo('}'))) {
            return new MapBasedVionObject();
        }
        VionObject result = new MapBasedVionObject();
        Map.Entry<String, Object> member = parseMember();
        extend(result, member);
        while (sequence.take(equalTo(','))) {
            member = parseMember();
            if (result.contains(member.getKey())) {
                throw sequence.error(
                        String.format("Duplicate key: '%s'", member.getKey()));
            }
            extend(result, member);
        }
        sequence.expect('}');
        return result;
    }

    public String parseChars() throws ParsingException {
        StringBuilder result = new StringBuilder();
        while (sequence.isCurrent(any(
                between('A', 'Z'), between('a', 'z'),
                between('0', '9'),
                equalTo('_')))) {
            result.append(sequence.take());
        }
        return result.toString();
    }

    Map.Entry<String, Object> parseMember() throws ParsingException {
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

    public List<Object> parseList() throws ParsingException {
        sequence.expect('[');
        sequence.skipWhitespaces();
        if (sequence.take(equalTo(']'))) {
            return new ArrayList<>();
        }
        List<Object> result = parseValues();
        sequence.expect(']');
        return result;
    }

    public List<Object> parseValues() throws ParsingException {
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

    public Number parseNumber() throws ParsingException {
        StringBuilder number = new StringBuilder();
        if (sequence.take(equalTo('-'))) {
            number.append('-');
        }
        while (sequence.isCurrent(in("0123456789"))) {
            number.append(sequence.take());
        }
        if (sequence.take(equalTo('.'))) {
            number.append('.');
            while (sequence.isCurrent(in("0123456789"))) {
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

    public String parseString() throws ParsingException {
        sequence.expect('"');
        StringBuilder result = new StringBuilder();
        while (!sequence.take(equalTo('"'))) {
            result.append(sequence.take());
        }
        return result.toString();
    }

    private void extend(VionObject vion, Map.Entry<String, Object> member) {
        String key = member.getKey();
        Object value = member.getValue();
        if (value instanceof String) {
            vion.put(key, (String) value);
        } else if (value instanceof Number) {
            vion.put(key, (Number) value);
        } else if (value instanceof VionObject) {
            vion.put(key, (VionObject) value);
        } else {
            throw new IllegalArgumentException(String.format(
                "Type %s is not supported",
                value.getClass()
            ));
        }
    }
}
