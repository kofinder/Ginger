package com.ginger;

public class MethodDefNode extends Node {
    private ScriptObject target;
    private final String name;
    private final Node body;

    public MethodDefNode(String name, Node body) {
        this.name = name;
        this.body = body;
    }

    public void setTarget(ScriptObject target) {
        this.target = target;
    }

    @Override
    public void interpret(GingerContext context) {
        target.setMethod(name, body);
    }
}
