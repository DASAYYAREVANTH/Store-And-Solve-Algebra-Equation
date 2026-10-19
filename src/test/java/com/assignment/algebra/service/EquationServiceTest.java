package com.assignment.algebra.service;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.assignment.algebra.dto.EvaluateEquationRequest;
import com.assignment.algebra.dto.EvaluateEquationResponse;
import com.assignment.algebra.dto.StoreEquationRequest;
import com.assignment.algebra.dto.StoreEquationResponse;
import com.assignment.algebra.error.ResourceNotFoundException;

class EquationServiceTest {
    @Test
    void storesListsAndEvaluatesEquation() {
        EquationService service = new EquationService();
        StoreEquationResponse stored = service.store(new StoreEquationRequest("3x + 2y - z"));
        assertEquals("1", stored.equationId());
        assertEquals(1, service.getAll().equations().size());
        EvaluateEquationResponse result = service.evaluate(stored.equationId(),
                new EvaluateEquationRequest(Map.of("x", 2.0, "y", 3.0, "z", 1.0)));
        assertEquals(11.0, result.result(), 1e-9);
    }

    @Test
    void returnsNotFoundForUnknownEquation() {
        EquationService service = new EquationService();
        assertThrows(ResourceNotFoundException.class,
                () -> service.evaluate("999", new EvaluateEquationRequest(Map.of())));
    }
}
