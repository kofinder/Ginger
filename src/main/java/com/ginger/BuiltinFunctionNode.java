package com.ginger;

public class BuiltinFunctionNode extends Node {
    private final String functionName;
    private final Node[] args;

    public BuiltinFunctionNode(String functionName, Node[] args) {
        this.functionName = functionName;
        this.args = args;
    }

    @Override
    public void interpret(GingerContext context) {
        if (functionName.equals("IO.print")) {
            for (Node arg : args) {
                arg.interpret(context);
                System.out.print(context.getVariable("_last"));
            }
            System.out.println();
        } else {
            throw new RuntimeException("Unknown builtin function: " + functionName);
        }
    }

}
