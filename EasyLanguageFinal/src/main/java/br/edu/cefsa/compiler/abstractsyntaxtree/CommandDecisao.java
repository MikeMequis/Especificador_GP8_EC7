package br.edu.cefsa.compiler.abstractsyntaxtree;

import java.util.ArrayList;

public class CommandDecisao extends AbstractCommand {

    private String condition;
    private ArrayList<AbstractCommand> listaTrue;
    private ArrayList<AbstractCommand> listaFalse;

    public CommandDecisao(String condition, ArrayList<AbstractCommand> lt, ArrayList<AbstractCommand> lf) {
        this.condition = condition;
        this.listaTrue = lt;
        this.listaFalse = lf;
    }

    @Override
    public void validate() throws Exception {
        if (semanticAnalyzer != null) {
            // Validate the condition expression
            String[] parts = condition.split(" ");
            for (String part : parts) {
                if (!part.matches("[=<>]") && !part.matches("\\d+(\\.\\d+)?")) {
                    semanticAnalyzer.validateExpression(part);
                }
            }
            
            // Set semantic analyzer for nested commands
            for (AbstractCommand cmd : listaTrue) {
                cmd.setSemanticAnalyzer(semanticAnalyzer);
                cmd.validate();
            }
            
            if (listaFalse != null) {
                for (AbstractCommand cmd : listaFalse) {
                    cmd.setSemanticAnalyzer(semanticAnalyzer);
                    cmd.validate();
                }
            }
        }
    }

    @Override
    public String generateJavaCode() {
        StringBuilder str = new StringBuilder();
        str.append("if (").append(condition).append(") {\n");
        for (AbstractCommand cmd : listaTrue) {
            str.append("\t").append(cmd.generateJavaCode()).append("\n");
        }
        str.append("}");
        if (listaFalse != null && !listaFalse.isEmpty()) {
            str.append(" else {\n");
            for (AbstractCommand cmd : listaFalse) {
                str.append("\t").append(cmd.generateJavaCode()).append("\n");
            }
            str.append("}\n");
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return "CommandDecisao [condition=" + condition + ", listaTrue=" + listaTrue + ", listaFalse=" + listaFalse
                + "]";
    }

}
