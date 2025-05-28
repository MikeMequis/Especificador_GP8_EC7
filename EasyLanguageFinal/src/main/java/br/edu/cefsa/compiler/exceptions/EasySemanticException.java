package br.edu.cefsa.compiler.exceptions;

import br.edu.cefsa.compiler.semantic.EasySemanticError;

public class EasySemanticException extends RuntimeException {
    private EasySemanticError error;

    public EasySemanticException(String msg) {
        this(new EasySemanticError(0, 0, msg, ""));
    }

    public EasySemanticException(EasySemanticError error) {
        super(error.toString());
        this.error = error;
    }

    public EasySemanticError getError() {
        return error;
    }
}
