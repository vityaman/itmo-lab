package ru.vityaman.tidb.lang.parse;

import org.junit.Assert;
import org.junit.Test;
import ru.vityaman.tidb.lang.interpreter.Instruction;

import java.util.List;
import java.util.Map;

public class ParserTest {

    private static void valid(Object expected, String source) {
        Assert.assertEquals(expected,
                new Parser(new StringSequence(source)).parseValue());
    }

    private static void validInstruction(Instruction expected, String source) {
        Assert.assertEquals(expected,
                new Parser(new StringSequence(source)).parseInstruction());
    }

    private static void invalid(String source) {
        Assert.assertThrows(ParsingException.class, () -> {
           new Parser(new StringSequence(source)).parseValue();
        });
    }

    @Test
    public void testList() {
        valid(List.of(), "[]");
        valid(List.of(), "[  \t ]");
        valid(List.of(1), "[1]");
        valid(List.of(1, 2, 3), "[1, 2, 3]");
        valid(List.of("a", 1, 1.1F), "[\"a\", 1, 1.1]");
        valid(List.of(List.of(), List.of(1, 2), 1), "[[], [1, 2], 1]");

        invalid("[");
        invalid("]");
        invalid("[,]");
        invalid("[1");
    }

    @Test
    public void testBool() {
        valid(true, "true");
        valid(false, "false");

        invalid("True");
        invalid("False");
        invalid("trUe");
    }

    @Test
    public void testNumber() {
        valid(12, "12");
        valid(-1, "-1");
        valid(0, "0");
        valid(0.01F, "0.01");
        valid(-0, "-0");
    }

    @Test
    public void testString() {
        valid("asas", "\"asas\"");
        valid("", "\"\"");

        invalid("sasad");
        invalid("");
    }

    @Test
    public void testObject() {
        valid(Map.of(), "{}");
        valid(Map.of(), "{   }");
        valid(Map.of("1", 1), "{1: 1}");
        valid(Map.of("string", "string"), "{string: \"string\"}");
        valid(Map.of("1", 1, "2", 2), "{1: 1, 2: 2}");
        valid(Map.of("1", 1, "2", 2), "{1:1,2:2}");
        valid(Map.of(
                "map", Map.of(
                        "true", true, "1", 1),
                "list", List.of(1, 2, 3)),

                "{map: {true: true, 1: 1}, list: [1, 2, 3]}");

        invalid("{");
        invalid("}");
        invalid("{key: sds");
    }

    @Test
    public void testTicket() {
        valid(
                Map.of(
                        "name", "Example",
                        "coordinates", Map.of(
                                "x", 10,
                                "y", 12
                            ),
                        "price", 12,
                        "type", "VIP",
                        "person", Map.of(
                              "height", 170,
                              "passportID", "vityaman",
                              "location", Map.of(
                                      "name", "Home",
                                        "x", 213,
                                        "y", 12321,
                                        "z", 2
                                )
                            )
                        ),

                "{\n" +
                        "    name: \"Example\",\n" +
                        "    coordinates: { x: 10, y: 12 },\n" +
                        "    price: 12,\n" +
                        "    type: \"VIP\",\n" +
                        "    person: { \n" +
                        "        height: 170, \n" +
                        "        passportID: \"vityaman\", \n" +
                        "        location: { name: \"Home\",\n" +
                        "            x: 213, y: 12321, z: 2\n" +
                        "        }\n" +
                        "    }\n" +
                        "}"
        );
    }

    @Test
    public void parseInstruction() {
        validInstruction(
                new Instruction("sum", List.of(1, 2)),
                "sum 1, 2");

        validInstruction(
                new Instruction("insert", List.of()),
                "insert ");

        validInstruction(
                new Instruction("map", List.of(Map.of("1", 1))),
                "map {1: 1}"
        );

        validInstruction(
                new Instruction("num", List.of(12)),
                "num 12\n"
        );

        validInstruction(
                new Instruction("exec", List.of("path")),
                "exec \"path\""
        );
    }

}