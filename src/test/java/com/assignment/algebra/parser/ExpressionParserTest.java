package com.assignment.algebra.parser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.assignment.algebra.error.InvalidInputException;

class ExpressionParserTest {
    private final ExpressionParser parser = new ExpressionParser();

    @Test
    void evaluatesLinearExpressionWithImplicitMultiplication() {
        ExpressionNode root = parser.parse("3x + 2y - z");
        assertEquals(11.0, root.evaluate(Map.of("x", 2.0, "y", 3.0, "z", 1.0)), 1e-9);
    }

    @Test
    void respectsParenthesesAndPrecedence() {
        assertEquals(14.0, parser.parse("2 + 3 * 4").evaluate(Map.of()), 1e-9);
        assertEquals(20.0, parser.parse("(2 + 3) * 4").evaluate(Map.of()), 1e-9);
    }

    @Test
    void supportsPower() {
        assertEquals(16.0, parser.parse("2^4").evaluate(Map.of()), 1e-9);
    }

    @Test
    void rejectsInvalidSyntax() {
        assertThrows(InvalidInputException.class, () -> parser.parse("2 + * 3"));
    }

    @Test
    void rejectsMissingVariable() {
        assertThrows(InvalidInputException.class, () -> parser.parse("x + 1").evaluate(Map.of()));
    }

    @Test
    void rejectsDivisionByZero() {
        assertThrows(InvalidInputException.class, () -> parser.parse("5 / (2 - 2)").evaluate(Map.of()));
    }
}
