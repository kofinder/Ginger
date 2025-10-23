package com.ginger;

public class ObjectInstantiationNode extends Node {
    private final String className;
    private final Node[] args;

    public ObjectInstantiationNode(String className, Node[] args) {
        this.className = className;
        this.args = args;
    }

    @Override
    public void interpret(GingerContext context) {
        var module = (ScriptObject) context.getVariable(className);
        if (module == null) {
            throw new RuntimeException("Class not found: " + className);
        }

        String[] paramNames = module.getConstructorParamNames();
        if (paramNames.length != args.length) {
            throw new RuntimeException("Argument count mismatch for " + className);
        }

        ScriptObject obj = new ScriptObject();

        for (int i = 0; i < args.length; i++) {
            args[i].interpret(context);
            obj.set(paramNames[i], context.getVariable("_last"));
        }

        for (String name : module.getMethodNames()) {
            obj.setMethod(name, module.getMethod(name));
        }

        context.setVariable("_last", obj);
    }
}
