package com.ginger;

import java.util.HashMap;
import java.util.Map;

public class ScriptObject {
    private final Map<String, Object> fields = new HashMap<>();
    private final Map<String, Node> methods = new HashMap<>();
    private String[] constructorParams = new String[0];

    // --- Fields ---
    public void set(String name, Object value) {
        fields.put(name, value);
    }

    public Object get(String name) {
        return fields.get(name);
    }

    public boolean hasField(String name) {
        return fields.containsKey(name);
    }

    public String[] getFieldNames() {
        return fields.keySet().toArray(new String[0]);
    }

    // --- Methods ---
    public void setMethod(String name, Node methodBody) {
        methods.put(name, methodBody);
    }

    public Node getMethod(String name) {
        return methods.get(name);
    }

    public String[] getMethodNames() {
        return methods.keySet().toArray(new String[0]);
    }

    // --- Constructor params ---
    public void setConstructorParams(String[] params) {
        this.constructorParams = params;
    }

    public String[] getConstructorParamNames() {
        return this.constructorParams;
    }

    // Optional: check if a method exists
    public boolean hasMethod(String name) {
        return methods.containsKey(name);
    }
}
