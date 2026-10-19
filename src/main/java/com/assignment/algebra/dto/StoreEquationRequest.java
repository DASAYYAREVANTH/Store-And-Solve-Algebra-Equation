package com.assignment.algebra.dto;

import jakarta.validation.constraints.NotBlank;

public record StoreEquationRequest(
        @NotBlank(message = "equation must not be blank") String equation) {
}
