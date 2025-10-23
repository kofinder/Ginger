package com.ginger;

public class ReturnNode extends Node {
    private final Node expr;

    public ReturnNode(Node expr) {
        this.expr = expr;
    }

    @Override
    public void interpret(GingerContext context) {
        expr.interpret(context);
        Object value = context.getVariable("_last");
        context.setVariable("_return", value);
    }
}
