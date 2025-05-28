package br.edu.cefsa.compiler.semantic;

public class EasySemanticError {
    private int line;
    private int column;
    private String message;
    private String code;

    public EasySemanticError(int line, int column, String message, String code) {
        this.line = line;
        this.column = column;
        this.message = message;
        this.code = code;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("Semantic Error at line %d, column %d: %s\nCode: %s", 
                           line, column, message, code);
    }
} 