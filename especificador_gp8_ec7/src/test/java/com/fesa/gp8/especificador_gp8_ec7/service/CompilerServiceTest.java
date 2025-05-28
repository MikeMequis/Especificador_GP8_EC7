package com.fesa.gp8.especificador_gp8_ec7.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.fesa.gp8.especificador_gp8_ec7.model.CompilationResult;

@SpringBootTest
public class CompilerServiceTest {

    @Autowired
    private CompilerService compilerService;

    @Test
    @DisplayName("Deve compilar um programa válido com sucesso")
    public void testValidProgram() {
        String sourceCode = """
            algoritmo "teste"
            var
                inteiro x
            inicio
                x = 10
            fimalgoritmo
            """;

        CompilationResult result = compilerService.compile(sourceCode);

        assertTrue(result.isSuccess());
        assertNotNull(result.getOutput());
        assertNotNull(result.getParseTree());
        assertNull(result.getError());
    }

    @Test
    @DisplayName("Deve identificar erro de sintaxe em programa inválido")
    public void testInvalidSyntax() {
        String sourceCode = """
            algoritmo "teste"
            var
                inteiro x
            inicio
                x = // erro de sintaxe aqui
            fimalgoritmo
            """;

        CompilationResult result = compilerService.compile(sourceCode);

        assertFalse(result.isSuccess());
        assertNotNull(result.getError());
        assertNull(result.getParseTree());
    }

    @Test
    @DisplayName("Deve compilar programa com todas as estruturas da linguagem")
    public void testComplexProgram() {
        String sourceCode = """
            algoritmo "complexo"
            var
                inteiro a, b
                real media
                literal nome
                logico aprovado
            inicio
                leia(a)
                leia(b)
                media = (a + b) / 2
                
                se media >= 6 entao
                    aprovado = verdadeiro
                senao
                    aprovado = falso
                fimse
                
                enquanto media < 6 faca
                    leia(a)
                    media = (media + a) / 2
                fimenquanto
                
                escreva(media)
            fimalgoritmo
            """;

        CompilationResult result = compilerService.compile(sourceCode);

        assertTrue(result.isSuccess());
        assertNotNull(result.getOutput());
        assertNotNull(result.getParseTree());
        assertNull(result.getError());
    }

    @Test
    @DisplayName("Deve identificar erro em declaração de variável inválida")
    public void testInvalidVariableDeclaration() {
        String sourceCode = """
            algoritmo "teste"
            var
                invalid_type x // tipo inválido
            inicio
                x = 10
            fimalgoritmo
            """;

        CompilationResult result = compilerService.compile(sourceCode);

        assertFalse(result.isSuccess());
        assertNotNull(result.getError());
    }

    @Test
    @DisplayName("Deve compilar programa com expressões lógicas complexas")
    public void testLogicalExpressions() {
        String sourceCode = """
            algoritmo "logica"
            var
                logico a, b, c
            inicio
                a = verdadeiro
                b = falso
                c = (a e b) ou (nao b)
            fimalgoritmo
            """;

        CompilationResult result = compilerService.compile(sourceCode);

        assertTrue(result.isSuccess());
        assertNotNull(result.getOutput());
        assertNotNull(result.getParseTree());
        assertNull(result.getError());
    }

    @Test
    @DisplayName("Deve identificar erro em programa sem seção de variáveis")
    public void testMissingVarSection() {
        String sourceCode = """
            algoritmo "teste"
            inicio
                x = 10
            fimalgoritmo
            """;

        CompilationResult result = compilerService.compile(sourceCode);

        assertFalse(result.isSuccess());
        assertNotNull(result.getError());
    }

    @Test
    @DisplayName("Deve compilar programa com strings e concatenação")
    public void testStringOperations() {
        String sourceCode = """
            algoritmo "strings"
            var
                literal nome, sobrenome
            inicio
                nome = "João"
                escreva(nome)
            fimalgoritmo
            """;

        CompilationResult result = compilerService.compile(sourceCode);

        assertTrue(result.isSuccess());
        assertNotNull(result.getOutput());
        assertNotNull(result.getParseTree());
        assertNull(result.getError());
    }

    @Test
    @DisplayName("Deve identificar erro em programa sem fimalgoritmo")
    public void testMissingEnd() {
        String sourceCode = """
            algoritmo "teste"
            var
                inteiro x
            inicio
                x = 10
            """.trim();

        CompilationResult result = compilerService.compile(sourceCode);

        assertFalse(result.isSuccess());
        assertNotNull(result.getError());
        assertTrue(result.getError().contains("EOF"));
    }
} 