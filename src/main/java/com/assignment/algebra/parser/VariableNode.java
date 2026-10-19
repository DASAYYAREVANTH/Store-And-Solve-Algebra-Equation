package com.assignment.algebra.parser;

import com.assignment.algebra.error.InvalidInputException;
import java.util.Map;
import java.util.Set;

public record VariableNode(String name) implements ExpressionNode {
    @Override
    public double evaluate(Map<String, Double> variables) {
        Double value = variables.get(name);
        if (value == null) throw new InvalidInputException("Missing value for variable: " + name);
        if (!Double.isFinite(value)) throw new InvalidInputException("Variable values must be finite numbers.");
        return value;
    }
    @Override public String toInfix() { return name; }
    @Override public void collectVariables(Set<String> names) { names.add(name); }
}
