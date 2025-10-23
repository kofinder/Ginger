package com.ginger;

public class MethodCallNode extends Node {
    private final Node objectExpr;
    private final String methodName;
    private final Node[] args;

    public MethodCallNode(Node objectExpr, String methodName, Node[] args) {
        this.objectExpr = objectExpr;
        this.methodName = methodName;
        this.args = args;
    }

    @Override
    public void interpret(GingerContext context) {
        objectExpr.interpret(context);
        ScriptObject obj = (ScriptObject) context.getVariable("_last");
        Node method = obj.getMethod(methodName);
        if (method == null)
            throw new RuntimeException("Method not found: " + methodName);

        // Check argument count
        if (args.length > 0) {
            String[] methodParams = obj.getConstructorParamNames(); // Or add getMethodParamNames if needed
            if (args.length != methodParams.length)
                throw new RuntimeException("Argument count mismatch for method " + methodName);

            for (int i = 0; i < args.length; i++) {
                args[i].interpret(context);
                obj.set(methodParams[i], context.getVariable("_last"));
            }
        }

        Object prev = context.getVariable("_currentObject");
        context.setVariable("_currentObject", obj);
        method.interpret(context);
        context.setVariable("_currentObject", prev);

    }

}
