package br.edu.cefsa.compiler.abstractsyntaxtree;

public class CommandEscrita extends AbstractCommand {

    private String id;
    private boolean withNewline;

    public CommandEscrita(String id) {
        this(id, true); // Default to escreval behavior
    }

    public CommandEscrita(String id, boolean withNewline) {
        this.id = id;
        this.withNewline = withNewline;
    }

    @Override
    public void validate() throws Exception {
        if (semanticAnalyzer != null && id.startsWith("$")) {
            // If it's a variable reference (starts with $), validate it
            String varName = id.substring(1);
            semanticAnalyzer.validateExpression(varName);
        }
    }

    @Override
    public String generateJavaCode() {
        String output = id;
        if (id.startsWith("$")) {
            // If it's a variable reference, remove the $ prefix
            output = id.substring(1);
        }
        if (withNewline) {
            return "System.out.println(" + output + ");";
        } else {
            return "System.out.print(" + output + ");";
        }
    }

    @Override
    public String toString() {
        return "CommandEscrita [id=" + id + ", withNewline=" + withNewline + "]";
    }

}
