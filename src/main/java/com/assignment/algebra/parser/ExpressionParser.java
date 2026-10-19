package com.assignment.algebra.parser;

import com.assignment.algebra.error.InvalidInputException;

/**
 * Recursive-descent parser supporting +, -, *, /, ^, parentheses, decimal numbers,
 * variable names, and implicit multiplication such as 3x or 2(x + 1).
 * Exponentiation is right-associative.
 */
public class ExpressionParser {
    private String input;
    private int position;

    public ExpressionNode parse(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new InvalidInputException("Equation must not be blank.");
        }
        input = expression.replaceAll("\\s+", "");
        position = 0;
        ExpressionNode result = parseExpression();
        if (position != input.length()) {
            throw error("Unexpected character '" + input.charAt(position) + "'");
        }
        return result;
    }

    private ExpressionNode parseExpression() {
        ExpressionNode node = parseTerm();
        while (peek('+') || peek('-')) {
            char op = input.charAt(position++);
            node = new BinaryNode(op, node, parseTerm());
        }
        return node;
    }

    private ExpressionNode parseTerm() {
        ExpressionNode node = parseUnary();
        while (true) {
            if (peek('*') || peek('/')) {
                char op = input.charAt(position++);
                node = new BinaryNode(op, node, parseUnary());
            } else if (startsPrimary()) {
                // Algebraic implicit multiplication: 3x, 2(x+1), (x+1)y.
                node = new BinaryNode('*', node, parseUnary());
            } else {
                return node;
            }
        }
    }

    private ExpressionNode parseUnary() {
        if (peek('+') || peek('-')) {
            char op = input.charAt(position++);
            return new UnaryNode(op, parseUnary());
        }
        return parsePower();
    }

    private ExpressionNode parsePower() {
        ExpressionNode node = parsePrimary();
        if (peek('^')) {
            position++;
            node = new BinaryNode('^', node, parseUnary());
        }
        return node;
    }

    private ExpressionNode parsePrimary() {
        if (position >= input.length()) throw error("Unexpected end of equation");
        if (peek('(')) {
            position++;
            ExpressionNode nested = parseExpression();
            if (!peek(')')) throw error("Missing closing parenthesis");
            position++;
            return nested;
        }
        char c = input.charAt(position);
        if (Character.isDigit(c) || c == '.') return parseNumber();
        if (Character.isLetter(c) || c == '_') return parseVariable();
        throw error("Unexpected character '" + c + "'");
    }

    private ExpressionNode parseNumber() {
        int start = position;
        boolean dotSeen = false;
        while (position < input.length()) {
            char c = input.charAt(position);
            if (Character.isDigit(c)) position++;
            else if (c == '.' && !dotSeen) { dotSeen = true; position++; }
            else break;
        }
        String source = input.substring(start, position);
        if (source.equals(".")) throw error("Invalid number");
        try {
            double value = Double.parseDouble(source);
            if (!Double.isFinite(value)) throw error("Number must be finite");
            return new NumberNode(value, source);
        } catch (NumberFormatException ex) {
            throw error("Invalid number '" + source + "'");
        }
    }

    private ExpressionNode parseVariable() {
        int start = position++;
        while (position < input.length()) {
            char c = input.charAt(position);
            if (Character.isLetterOrDigit(c) || c == '_') position++;
            else break;
        }
        return new VariableNode(input.substring(start, position));
    }

    private boolean startsPrimary() {
        if (position >= input.length()) return false;
        char c = input.charAt(position);
        return c == '(' || Character.isLetter(c) || c == '_' || Character.isDigit(c) || c == '.';
    }

    private boolean peek(char c) {
        return position < input.length() && input.charAt(position) == c;
    }

    private InvalidInputException error(String message) {
        return new InvalidInputException(message + " at position " + position);
    }
}
