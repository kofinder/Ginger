package com.ginger;

public class AssignmentNode extends Node {
    private final String varName;
    private final Node valueNode;

    public AssignmentNode(String varName, Node valueNode) {
        this.varName = varName;
        this.valueNode = valueNode;
    }

    @Override
    public void interpret(GingerContext context) {
        valueNode.interpret(context);
        Object val = context.getVariable("_last");
        context.setVariable(varName, val);
    }

}
