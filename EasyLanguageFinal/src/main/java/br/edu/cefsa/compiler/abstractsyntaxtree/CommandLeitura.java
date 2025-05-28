package br.edu.cefsa.compiler.abstractsyntaxtree;

import br.edu.cefsa.compiler.datastructures.EasyVariable;

public class CommandLeitura extends AbstractCommand {

    private String id;
    private EasyVariable var;

    public CommandLeitura(String id, EasyVariable var) {
        this.id = id;
        this.var = var;
    }

    @Override
    public void validate() throws Exception {
        if (semanticAnalyzer != null) {
            // Verify the variable exists and is declared
            semanticAnalyzer.resolveVariable(id);
            // Mark the variable as initialized after reading
            semanticAnalyzer.markInitialized(id);
        }
    }

    @Override
    public String generateJavaCode() {
        String inputMethod;
        switch (var.getType()) {
            case EasyVariable.INTEGER:
                inputMethod = "nextInt()";
                break;
            case EasyVariable.REAL:
                inputMethod = "nextDouble()";
                break;
            case EasyVariable.BOOLEAN:
                inputMethod = "nextBoolean()";
                break;
            default:
                inputMethod = "nextLine()";
        }
        return id + " = _key." + inputMethod + ";";
    }

    @Override
    public String toString() {
        return "CommandLeitura [id=" + id + "]";
    }

}
