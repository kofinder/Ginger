package com.ginger;

public class LiteralNode extends Node {
    private final String value;

    public LiteralNode(String value) {
        this.value = value;
    }

    @Override
    public void interpret(GingerContext context) {
        context.setVariable("_last", value);
    }

}
