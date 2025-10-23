package com.ginger;

import java.util.*;
import java.util.regex.*;

public class Lexer {
    private static final Pattern tokenPatterns = Pattern.compile(
            "\\s*(?:(module|def|do|end|ret|let|IO)|([a-zA-Z_][a-zA-Z0-9_]*)|(\\d+)|([()=;:+\\-*/,\\.]))");

    private final String input;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        Matcher m = tokenPatterns.matcher(input);
        while (m.find()) {
            if (m.group(1) != null) {
                tokens.add(new Token(Token.Type.KEYWORD, m.group(1)));
            } else if (m.group(2) != null) {
                tokens.add(new Token(Token.Type.IDENTIFIER, m.group(2)));
            } else if (m.group(3) != null) {
                tokens.add(new Token(Token.Type.NUMBER, m.group(3)));
            } else if (m.group(4) != null) {
                tokens.add(new Token(Token.Type.SYMBOL, m.group(4)));
            }
        }
        tokens.add(new Token(Token.Type.EOF, ""));
        return tokens;
    }
}
