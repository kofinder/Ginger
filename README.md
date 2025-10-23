# GingerScript

GingerScript is a **mini Java-based interpreter** for a custom DSL inspired by scripting languages. It allows developers to define **modules**, **methods**, and perform **basic arithmetic operations** with a simple syntax.

---

## Example Script

```ginger
module User(x, y) do
    def calculate do
        ret x * y;
    end
end

let user = User(3, 4);

IO.print(user.calculate());
```

### What this script does:

1. Declares a `User` module with parameters `x` and `y`.  
2. Defines a `calculate` method that multiplies `x` and `y`.  
3. Instantiates a `User` object with `x=3` and `y=4`.  
4. Calls the `calculate` method and prints the result using the built-in `IO.print()` function.

**Expected Output:**

```
12
```

---

## Project Structure

```
gingerscript/
├── src/main/java/com/ginger
│   ├── Application.java       # Entry point
│   ├── Parser.java            # Parses tokens into AST
│   ├── Lexer.java             # Tokenizes input script
│   ├── Node.java              # Base AST node class
│   ├── ScriptObject.java      # Represents objects/modules
│   └── [Other AST Nodes]     # BinaryOpNode, MethodCallNode, etc.
├── src/main/resources
│   └── program.ginger         # Example script file
├── build.gradle
└── README.md
```

---

## Features

- **Module system** – Define modules with constructor parameters.  
- **Methods** – Methods with return values.  
- **Object instantiation** – Create module instances dynamically.  
- **Method calls** – Call methods on objects.  
- **Built-in functions** – Currently supports `IO.print()`.  
- **Arithmetic expressions** – `+`, `-`, `*`, `/`.  
- **Script execution** – Input can be from a file or inline string.  

---

## How to Run

1. Clone the repository:  
```bash
git clone <repo-url>
cd gingerscript
```

2. Build the project with Gradle:
```bash
./gradlew build
```

3. Run the interpreter:
```bash
./gradlew run
```

Or, if using the `program.ginger` script file:
```java
// In Application.java
Path path = Path.of("src/main/resources/program.ginger");
String input = Files.readString(path, StandardCharsets.UTF_8);
```

4. The output of the script will be printed to the console.

---

## Contributing

- Add new built-in functions by extending `BuiltinFunctionNode`.  
- Add new AST nodes for more language features.  
- Improve the parser to handle complex expressions and statements.

---

## License

MIT License