package com.assignment.algebra.api;

import com.assignment.algebra.dto.*;
import com.assignment.algebra.service.EquationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equations")
public class EquationController {
    private final EquationService equationService;

    public EquationController(EquationService equationService) {
        this.equationService = equationService;
    }

    @PostMapping("/store")
    @ResponseStatus(HttpStatus.CREATED)
    public StoreEquationResponse store(@Valid @RequestBody StoreEquationRequest request) {
        return equationService.store(request);
    }

    @GetMapping
    public EquationListResponse getAll() {
        return equationService.getAll();
    }

    @PostMapping("/{equationId}/evaluate")
    public EvaluateEquationResponse evaluate(
            @PathVariable String equationId,
            @Valid @RequestBody EvaluateEquationRequest request) {
        return equationService.evaluate(equationId, request);
    }
}
