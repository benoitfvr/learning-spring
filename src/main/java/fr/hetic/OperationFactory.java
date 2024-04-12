package fr.hetic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class OperationFactory {
    private static final Map<String, Supplier<Operation>> operations = new HashMap<>();

    static {
        operations.put("+", Addition::new);
        operations.put("-", Subtraction::new);
        operations.put("*", Multiplication::new);
    }

    public static Operation getOperation(String operator) {
        if (!operations.containsKey(operator)) {
            throw new IllegalArgumentException("Unknown operation: " + operator);
        }
        return operations.get(operator).get();
    }
}
