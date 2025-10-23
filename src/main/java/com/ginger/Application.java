package com.ginger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("src/main/resources/program.ginger");
        String input = Files.readString(path, StandardCharsets.UTF_8);

        var lexer = new Lexer(input);
        var parser = new Parser(lexer.tokenize());
        var context = new GingerContext();
        for (Node node : parser.parseProgram()) {
            node.interpret(context);
        }
    }
}
