package br.edu.cefsa.compiler.abstractsyntaxtree;

import br.edu.cefsa.compiler.datastructures.EasyVariable;

public class CommandAtribuicao extends AbstractCommand {

    private String id;
    private String expr;
    private int exprType;

    public CommandAtribuicao(String id, String expr) {
        this.id = id;
        this.expr = expr;
        this.exprType = -1; // Will be set during validation
    }

    @Override
    public void validate() throws Exception {
        if (semanticAnalyzer != null) {
            // Validate the target variable exists
            semanticAnalyzer.resolveVariable(id);
            
            // Validate the expression and get its type
            String[] parts = expr.split(" ");
            if (parts.length == 1) {
                // Simple assignment
                if (parts[0].matches("\\d+(\\.\\d+)?")) {
                    // Numeric literal
                    exprType = parts[0].contains(".") ? EasyVariable.REAL : EasyVariable.INTEGER;
                } else if (parts[0].equals("verdadeiro") || parts[0].equals("falso")) {
                    exprType = EasyVariable.BOOLEAN;
                } else if (parts[0].startsWith("\"") && parts[0].endsWith("\"")) {
                    exprType = EasyVariable.TEXT;
                } else {
                    // Variable reference
                    EasyVariable var = semanticAnalyzer.resolveVariable(parts[0]);
                    semanticAnalyzer.validateExpression(parts[0]);
                    exprType = var.getType();
                }
            } else {
                // Complex expression
                for (String part : parts) {
                    if (!part.matches("[+\\-*/=<>]") && !part.matches("\\d+(\\.\\d+)?")) {
                        semanticAnalyzer.validateExpression(part);
                    }
                }
                // For now, assume the type based on the operation
                // This should be enhanced with proper expression type inference
                exprType = semanticAnalyzer.resolveVariable(id).getType();
            }
            
            // Validate the assignment compatibility
            semanticAnalyzer.validateAssignment(id, expr, exprType);
        }
    }

    @Override
    public String generateJavaCode() {
        return id + " = " + expr + ";";
    }

    @Override
    public String toString() {
        return "CommandAtribuicao [id=" + id + ", expr=" + expr + "]";
    }

}
