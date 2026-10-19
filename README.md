# Algebra Equation API

A Java 21 / Spring Boot REST API for storing algebraic expressions, listing saved equations, and evaluating them using variable values. Equations are parsed into an expression tree and held in memory, as requested by the assignment.

## Features

- `POST /api/equations/store` — parse and store an expression.
- `GET /api/equations` — list stored expressions.
- `POST /api/equations/{equationId}/evaluate` — evaluate a stored expression.
- Supports `+`, `-`, `*`, `/`, `^`, parentheses, decimal numbers, variable names, unary signs, and implicit multiplication such as `3x`.
- Validates malformed expressions, missing variables, division by zero, unknown IDs, and non-finite results.
- Swagger UI at `/swagger-ui/index.html`.
- JUnit 5 unit tests.

## Run locally

Requirements: JDK 21 and Maven 3.9+.

```bash
mvn test
mvn spring-boot:run
```

Base URL: `http://localhost:8081`

Swagger UI: `http://localhost:8081/swagger-ui/index.html`

## Test with curl

Store:
```bash
curl -X POST http://localhost:8081/api/equations/store \
  -H "Content-Type: application/json" \
  -d '{"equation":"3x + 2y - z"}'
```

Evaluate (use the `equationId` returned by the store request):
```bash
curl -X POST http://localhost:8081/api/equations/1/evaluate \
  -H "Content-Type: application/json" \
  -d '{"variables":{"x":2,"y":3,"z":1}}'
```

List:
```bash
curl http://localhost:8081/api/equations
```

Expected evaluation for `3x + 2y - z`, with `x=2`, `y=3`, `z=1`: `10`.

## Assumptions and limitations

- Storage is in memory; all equations are cleared when the process restarts.
- Expression syntax is arithmetic algebra, not equality expressions (for example, `3x + 2y - z`, not `y = 2x`).
- Variable names may contain letters, digits after the first character, and underscores.
- Implicit multiplication is supported, for example `3x`, `2(x+1)`, and `(x+1)y`.
- The expression parser supports standard arithmetic operations only; functions such as `sin(x)` are not included.
- This is a REST-only project; no frontend is included.

## Deployment

Deploy the repository to a Java-capable hosting provider that supports Maven builds. Set the service to build with `mvn clean package` and start with `java -jar target/algebra-equation-api-1.0.0.jar`. The application listens on the `PORT` environment variable when provided, otherwise `8081`.

## Suggested demo flow

1. Open Swagger UI.
2. Store `3x + 2y - z`.
3. List saved equations and copy the ID.
4. Evaluate with `x=2`, `y=3`, `z=1`; show result `10`.
5. Demonstrate an invalid expression and a missing variable to show error handling.
