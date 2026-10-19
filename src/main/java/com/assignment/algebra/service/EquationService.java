package com.assignment.algebra.service;

import com.assignment.algebra.dto.*;
import com.assignment.algebra.error.InvalidInputException;
import com.assignment.algebra.error.ResourceNotFoundException;
import com.assignment.algebra.model.EquationRecord;
import com.assignment.algebra.parser.ExpressionNode;
import com.assignment.algebra.parser.ExpressionParser;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EquationService {
    private final Map<String, EquationRecord> equations = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);
    private final ExpressionParser parser = new ExpressionParser();

    public StoreEquationResponse store(StoreEquationRequest request) {
        ExpressionNode root = parser.parse(request.equation());
        String id = Long.toString(sequence.incrementAndGet());
        EquationRecord record = new EquationRecord(id, root.toInfix(), root);
        equations.put(id, record);
        return new StoreEquationResponse("Equation stored successfully", id, record.equation());
    }

    public EquationListResponse getAll() {
        List<EquationSummary> summaries = equations.values().stream()
                .sorted(Comparator.comparingLong(record -> Long.parseLong(record.id())))
                .map(record -> new EquationSummary(record.id(), record.equation()))
                .toList();
        return new EquationListResponse(summaries);
    }

    public EvaluateEquationResponse evaluate(String id, EvaluateEquationRequest request) {
        EquationRecord record = equations.get(id);
        if (record == null) throw new ResourceNotFoundException("Equation not found: " + id);
        Map<String, Double> variables = request.variables() == null
                ? Map.of() : new LinkedHashMap<>(request.variables());
        if (variables.values().stream().anyMatch(Objects::isNull)) {
            throw new InvalidInputException("Variable values must not be null.");
        }
        double result = record.root().evaluate(variables);
        return new EvaluateEquationResponse(record.id(), record.equation(),
                Collections.unmodifiableMap(variables), result);
    }
}
