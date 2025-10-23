package com.ginger;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // --- Utility ---
    private Token peek() {
        if (pos >= tokens.size())
            return new Token(Token.Type.EOF, "EOF");
        return tokens.get(pos);
    }

    private Token next() {
        return tokens.get(pos++);
    }

    private void expect(String value) {
        if (!next().value.equals(value))
            throw new RuntimeException("Expected '" + value + "'");
    }

    private String expectIdentifier() {
        Token t = next();
        if (t.type != Token.Type.IDENTIFIER)
            throw new RuntimeException("Expected identifier, got " + t.value);
        return t.value;
    }

    // --- Top-level parse ---
    public Node[] parseProgram() {
        List<Node> nodes = new ArrayList<>();
        while (!peek().value.equals("") && !peek().value.equals("EOF")) {
            nodes.add(parseStatement());
        }
        return nodes.toArray(new Node[0]);
    }

    private Node parseStatement() {
        String val = peek().value;

        switch (val) {
            case "module":
                return parseModule();
            case "let":
                return parseAssignment();
            case "ret":
                return parseReturn();
            case "IO":
                return parseBuiltin();
            default:
                return parseExpressionStatement();
        }
    }

    // --- Module ---
    private ModuleNode parseModule() {
        expect("module");
        String name = expectIdentifier();
        expect("(");
        String[] params = parseParams();
        expect(")");
        expect("do");

        Node[] body = parseModuleBody();

        expect("end");
        return new ModuleNode(name, params, body);
    }

    private Node[] parseModuleBody() {
        List<Node> nodes = new ArrayList<>();
        while (!peek().value.equals("end")) {
            String val = peek().value;
            if (val.equals("def")) {
                nodes.add(parseMethod());
            } else if (val.equals("ret") || val.equals("let") || val.equals("IO")) {
                nodes.add(parseStatement());
            } else {
                nodes.add(parseExpressionStatement());
            }
        }
        return nodes.toArray(new Node[0]);
    }

    private String[] parseParams() {
        List<String> list = new ArrayList<>();
        list.add(expectIdentifier());
        while (peek().value.equals(",")) {
            next();
            list.add(expectIdentifier());
        }
        return list.toArray(new String[0]);
    }

    // --- Method ---
    private MethodDefNode parseMethod() {
        expect("def");
        String name = expectIdentifier();
        expect("do");
        Node body = parseReturn(); // assume only ret statements
        expect("end");
        return new MethodDefNode(name, body);
    }

    // --- Return ---
    private ReturnNode parseReturn() {
        expect("ret");
        Node expr = parseExpression();
        expect(";");
        return new ReturnNode(expr);
    }

    // --- Assignment ---
    private AssignmentNode parseAssignment() {
        expect("let");
        String varName = expectIdentifier();
        expect("=");
        Node expr = parseExpression();
        expect(";");
        return new AssignmentNode(varName, expr);
    }

    // --- Builtin ---
    private Node parseBuiltin() {
        expect("IO");
        expect(".");
        String funcName = "IO." + expectIdentifier();
        expect("(");
        List<Node> args = new ArrayList<>();
        if (!peek().value.equals(")")) {
            args.add(parseExpression());
            while (peek().value.equals(",")) {
                next();
                args.add(parseExpression());
            }
        }
        expect(")");
        expect(";");
        return new BuiltinFunctionNode(funcName, args.toArray(new Node[0]));
    }

    // --- Expression statements ---
    private Node parseExpressionStatement() {
        Node expr = parseExpression();
        expect(";");
        return expr;
    }

    // --- Expressions ---
    private Node parseExpression() {
        Node left = parseTerm();
        while (peek().value.equals("+") || peek().value.equals("-") ||
                peek().value.equals("*") || peek().value.equals("/")) {
            String op = next().value;
            Node right = parseTerm();
            left = new BinaryOpNode(left, op, right);
        }
        return left;
    }

    private Node parseTerm() {
        Token t = next();
        switch (t.type) {
            case NUMBER:
                return new LiteralNode(t.value);
            case IDENTIFIER:
                if (peek().value.equals("(") || peek().value.equals(".")) {
                    return parseMethodCall(new VariableNode(t.value));
                } else {
                    return new VariableNode(t.value);
                }
            case SYMBOL:
                if (t.value.equals("(")) {
                    Node expr = parseExpression();
                    expect(")");
                    return expr;
                } else {
                    throw new RuntimeException("Unexpected symbol: " + t.value);
                }
            default:
                throw new RuntimeException("Unexpected token: " + t);
        }
    }

    // --- Method call / object instantiation ---
    private Node parseMethodCall(Node objectExpr) {
        if (peek().value.equals("(")) {
            // Object instantiation
            next(); // skip '('
            List<Node> args = new ArrayList<>();
            if (!peek().value.equals(")")) {
                args.add(parseExpression());
                while (peek().value.equals(",")) {
                    next();
                    args.add(parseExpression());
                }
            }
            expect(")");
            return new ObjectInstantiationNode(((VariableNode) objectExpr).getName(),
                    args.toArray(new Node[0]));
        } else if (peek().value.equals(".")) {
            // Method call
            next(); // skip '.'
            String methodName = expectIdentifier();
            expect("(");
            List<Node> args = new ArrayList<>();
            if (!peek().value.equals(")")) {
                args.add(parseExpression());
                while (peek().value.equals(",")) {
                    next();
                    args.add(parseExpression());
                }
            }
            expect(")");
            return new MethodCallNode(objectExpr, methodName, args.toArray(new Node[0]));
        }
        return objectExpr;
    }
}
