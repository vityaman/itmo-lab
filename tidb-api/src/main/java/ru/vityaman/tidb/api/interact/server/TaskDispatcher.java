package ru.vityaman.tidb.api.interact.server;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import ru.vityaman.tidb.api.interact.MethodCall;
import ru.vityaman.tidb.api.method.Method;

public final class TaskDispatcher {
    private final Map<Method, TaskFactory> factoryByMethod;

    public TaskDispatcher(Map<Method, TaskFactory> factoryByMethod) {
        if (factoryByMethod == null) {
            throw new NullPointerException(
                "argument factoryByMethod is null"
            );
        }
        if (factoryByMethod.size() != Method.values().length) {
            throw new IllegalArgumentException(
                "factoryByMethod is invalid as all methods must be set"
            );
        }
        this.factoryByMethod = new EnumMap<>(factoryByMethod);
    }

    public Runnable deserialize(MethodCall call) throws IOException {
        return factoryByMethod.get(call.method())
                              .createFrom(call.payload());
    }
}
