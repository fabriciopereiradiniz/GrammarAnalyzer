# ⚪ GrammarAnalyzer
| ![instruction_process](https://i.imgur.com/1jZRPKp.gif) | This is a Java application that deals with context-free grammars, particularly with GLUE (Generalized Left-to-right, Unrestricted, Erasing) and GLUD (Generalized Left-to-right, Unrestricted, Deleting) grammars. It allows you to input the grammar rules and test whether a given word can be generated by the grammar. Check the technical report [HERE](./relatorio.pdf). |
|---|---|

## Compilation and Execution of the Program

Make sure you have the Java Development Kit (JDK) installed on your machine. Open a terminal or command prompt and navigate to the directory where the program folder `scr` is located.
After compilation of the archives, execute the `Program`.

## How to Use

1. Compile and run the Program class.
2. You will be prompted to choose between GLUE (0) and GLUD (1) grammars.
3. Enter the number of variables `<V>` and the variables themselves.
4. Enter the number of characters in the alphabet `<T>` and the characters.
5. Specify the starting symbol `<S>`.
6. Enter the number of production rules.
7. Provide each production rule in the format `A>B`, where A is a variable, and B is a sequence of terminals and variables.
8. - For GLUE grammar, use the format A>Ab.
   - For GLUD grammar, use the format A>bA.
9. Input the word you want to test against the grammar.

## Usage

Follow the prompts to provide input and test your grammar. The program will generate the productions that lead to the provided word if it's derivable from the grammar.

## Notes

- The application handles GLUE (0) and GLUD (1) grammars.
- Production rules should be entered in the correct format according to the chosen grammar type.
