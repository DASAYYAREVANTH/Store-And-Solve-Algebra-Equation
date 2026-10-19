package com.assignment.algebra.parser;

import java.util.Map;
import java.util.Set;

public sealed interface ExpressionNode permits NumberNode, VariableNode, UnaryNode, BinaryNode {
    double evaluate(Map<String, Double> variables);
    String toInfix();
    void collectVariables(Set<String> names);
}
