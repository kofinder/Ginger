package com.ginger;

public class PrintNode extends Node {
    private final Node expr;

    public PrintNode(Node expr) {
        this.expr = expr;
    }

    @Override
    public void interpret(GingerContext context) {
        expr.interpret(context);
        System.out.println(context.getVariable("_last"));
    }
}
