package com.ginger;

public class VariableNode extends Node {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void interpret(GingerContext context) {
        Object value = null;
        ScriptObject current = (ScriptObject) context.getVariable("_currentObject");
        if (current != null && current.hasField(name)) {
            value = current.get(name);
        } else if (context.hasVariable(name)) {
            value = context.getVariable(name);
        }

        if (value == null) {
            throw new RuntimeException("Variable not defined: " + name);
        }

        context.setVariable("_last", value);
    }
}
