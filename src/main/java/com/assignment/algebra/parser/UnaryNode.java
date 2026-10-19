package com.assignment.algebra.parser;

import java.util.Map;
import java.util.Set;

public record UnaryNode(char operator, ExpressionNode child) implements ExpressionNode {
    @Override
    public double evaluate(Map<String, Double> variables) {
        double value = child.evaluate(variables);
        return operator == '-' ? -value : value;
    }
    @Override public String toInfix() { return "(" + operator + child.toInfix() + ")"; }
    @Override public void collectVariables(Set<String> names) { child.collectVariables(names); }
}
