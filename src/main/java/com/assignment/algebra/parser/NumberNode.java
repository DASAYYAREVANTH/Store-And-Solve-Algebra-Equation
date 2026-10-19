package com.assignment.algebra.parser;

import java.util.Map;
import java.util.Set;

public record NumberNode(double value, String source) implements ExpressionNode {
    @Override public double evaluate(Map<String, Double> variables) { return value; }
    @Override public String toInfix() { return source; }
    @Override public void collectVariables(Set<String> names) { }
}
