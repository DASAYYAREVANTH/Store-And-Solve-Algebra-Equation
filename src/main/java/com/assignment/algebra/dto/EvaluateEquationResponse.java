package com.assignment.algebra.dto;

import java.util.Map;

public record EvaluateEquationResponse(String equationId, String equation,
                                       Map<String, Double> variables, double result) {
}
