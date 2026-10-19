package com.assignment.algebra.parser;

import com.assignment.algebra.error.InvalidInputException;
import java.util.Map;
import java.util.Set;

public record BinaryNode(char operator, ExpressionNode left, ExpressionNode right) implements ExpressionNode {
    @Override
    public double evaluate(Map<String, Double> variables) {
        double a = left.evaluate(variables);
        double b = right.evaluate(variables);
        double result = switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0.0) throw new InvalidInputException("Division by zero is not allowed.");
                yield a / b;
            }
            case '^' -> Math.pow(a, b);
            default -> throw new InvalidInputException("Unsupported operator: " + operator);
        };
        if (!Double.isFinite(result)) throw new InvalidInputException("Expression result is not a finite number.");
        return result;
    }
    @Override public String toInfix() {
        return "(" + left.toInfix() + " " + operator + " " + right.toInfix() + ")";
    }
    @Override public void collectVariables(Set<String> names) {
        left.collectVariables(names);
        right.collectVariables(names);
    }
}
