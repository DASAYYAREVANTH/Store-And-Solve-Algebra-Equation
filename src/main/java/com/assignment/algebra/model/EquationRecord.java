package com.assignment.algebra.model;

import com.assignment.algebra.parser.ExpressionNode;

public record EquationRecord(String id, String equation, ExpressionNode root) {
}
