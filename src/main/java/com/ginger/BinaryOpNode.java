package com.ginger;

public class BinaryOpNode extends Node {
    private final Node left;
    private final String operator;
    private final Node right;

    public BinaryOpNode(Node left, String operator, Node right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public void interpret(GingerContext context) {
        left.interpret(context);
        Object l = context.getVariable("_last");

        right.interpret(context);
        Object r = context.getVariable("_last");

        switch (operator) {
            case "+":
                context.setVariable("_last", toInt(l) + toInt(r));
                break;
            case "-":
                context.setVariable("_last", toInt(l) - toInt(r));
                break;
            case "*":
                context.setVariable("_last", toInt(l) * toInt(r));
                break;
            case "/":
                context.setVariable("_last", toInt(l) / toInt(r));
                break;
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }

    private int toInt(Object o) {
        if (o instanceof Number)
            return ((Number) o).intValue();
        if (o instanceof String)
            return Integer.parseInt((String) o);
        throw new RuntimeException("Cannot convert to int: " + o);
    }
}
