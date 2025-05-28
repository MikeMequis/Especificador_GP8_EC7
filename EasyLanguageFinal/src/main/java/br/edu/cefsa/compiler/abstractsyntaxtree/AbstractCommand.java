package br.edu.cefsa.compiler.abstractsyntaxtree;

import br.edu.cefsa.compiler.semantic.EasySemanticAnalyzer;

public abstract class AbstractCommand {
    protected EasySemanticAnalyzer semanticAnalyzer;

    public void setSemanticAnalyzer(EasySemanticAnalyzer analyzer) {
        this.semanticAnalyzer = analyzer;
    }

    public abstract void validate() throws Exception;
    public abstract String generateJavaCode();
}
