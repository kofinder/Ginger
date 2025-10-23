package com.ginger;

public class ModuleNode extends Node {
    private final String name;
    private final String[] params;
    private final Node[] body;

    public ModuleNode(String name, String[] params, Node[] body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public void interpret(GingerContext context) {
        ScriptObject module = new ScriptObject();
        module.setConstructorParams(params); // preserve parameter order
        context.setVariable(name, module);

        for (Node node : body) {
            if (node instanceof MethodDefNode) {
                ((MethodDefNode) node).setTarget(module);
            }
            node.interpret(context);
        }
    }

}
