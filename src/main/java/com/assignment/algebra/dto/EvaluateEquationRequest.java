package com.assignment.algebra.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record EvaluateEquationRequest(@NotNull(message = "variables must be provided")
                                      Map<String, Double> variables) {
}
