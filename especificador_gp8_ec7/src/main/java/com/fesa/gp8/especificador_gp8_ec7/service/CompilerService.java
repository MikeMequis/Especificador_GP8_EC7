package com.fesa.gp8.especificador_gp8_ec7.service;

import org.antlr.v4.runtime.*;
import org.springframework.stereotype.Service;
import com.fesa.gp8.especificador_gp8_ec7.model.CompilationResult;
import br.edu.cefsa.compiler.parser.EasyLanguageLexer;
import br.edu.cefsa.compiler.parser.EasyLanguageParser;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompilerService {
    
    private static class SyntaxErrorListener extends BaseErrorListener {
        private final List<String> errors = new ArrayList<>();
        
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                              Object offendingSymbol,
                              int line,
                              int charPositionInLine,
                              String msg,
                              RecognitionException e) {
            errors.add(String.format("Linha %d:%d - %s", line, charPositionInLine, msg));
        }
        
        public List<String> getErrors() {
            return errors;
        }
        
        public boolean hasErrors() {
            return !errors.isEmpty();
        }
    }
    
    public CompilationResult compile(String sourceCode) {
        try {
            // Create input stream from source code
            CharStream input = CharStreams.fromStream(
                new ByteArrayInputStream(sourceCode.getBytes(StandardCharsets.UTF_8))
            );
            
            // Create lexer and parser with custom error listener
            EasyLanguageLexer lexer = new EasyLanguageLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            EasyLanguageParser parser = new EasyLanguageParser(tokens);
            
            // Remove default error listeners and add custom one
            SyntaxErrorListener errorListener = new SyntaxErrorListener();
            lexer.removeErrorListeners();
            parser.removeErrorListeners();
            lexer.addErrorListener(errorListener);
            parser.addErrorListener(errorListener);
            
            // Parse the input
            EasyLanguageParser.ProgContext tree = parser.prog();
            
            // Check for syntax errors
            if (errorListener.hasErrors()) {
                return CompilationResult.builder()
                    .success(false)
                    .error("Erros de compilação:\n" + String.join("\n", errorListener.getErrors()))
                    .build();
            }
            
            return CompilationResult.builder()
                .success(true)
                .output("Compilação realizada com sucesso!")
                .parseTree(tree.toStringTree(parser))
                .build();
                
        } catch (Exception e) {
            return CompilationResult.builder()
                .success(false)
                .error("Erro de compilação: " + e.getMessage())
                .build();
        }
    }
    
    public CompilationResult run(String sourceCode) {
        return compile(sourceCode);
    }
} 