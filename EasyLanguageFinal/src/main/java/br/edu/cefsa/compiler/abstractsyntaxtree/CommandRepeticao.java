package br.edu.cefsa.compiler.abstractsyntaxtree;

import java.util.ArrayList;

public class CommandRepeticao extends AbstractCommand {
    private String condition;
    private ArrayList<AbstractCommand> commands;

    public CommandRepeticao(String condition, ArrayList<AbstractCommand> commands) {
        this.condition = condition;
        this.commands = commands;
    }

    @Override
    public void validate() throws Exception {
        if (semanticAnalyzer != null) {
            // Enter loop context
            semanticAnalyzer.enterLoop();
            
            try {
                // Validate the condition expression
                String[] parts = condition.split(" ");
                for (String part : parts) {
                    if (!part.matches("[=<>]") && !part.matches("\\d+(\\.\\d+)?")) {
                        semanticAnalyzer.validateExpression(part);
                    }
                }
                
                // Set semantic analyzer for nested commands
                for (AbstractCommand cmd : commands) {
                    cmd.setSemanticAnalyzer(semanticAnalyzer);
                    cmd.validate();
                }
            } finally {
                // Always exit loop context
                semanticAnalyzer.exitLoop();
            }
        }
    }

    @Override
    public String generateJavaCode() {
        StringBuilder str = new StringBuilder();
        str.append("while (").append(condition).append(") {\n");
        for (AbstractCommand cmd : commands) {
            str.append("\t").append(cmd.generateJavaCode()).append("\n");
        }
        str.append("}\n");
        return str.toString();
    }

    @Override
    public String toString() {
        return "CommandRepeticao [condition=" + condition + ", commands=" + commands + "]";
    }
} 