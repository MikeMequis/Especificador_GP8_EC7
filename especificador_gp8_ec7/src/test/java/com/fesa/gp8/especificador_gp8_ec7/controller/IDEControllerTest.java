package com.fesa.gp8.especificador_gp8_ec7.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fesa.gp8.especificador_gp8_ec7.service.CompilerService;
import com.fesa.gp8.especificador_gp8_ec7.service.ExampleService;
import com.fesa.gp8.especificador_gp8_ec7.model.CompilationResult;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(IDEController.class)
public class IDEControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompilerService compilerService;
    
    @Mock
    private ExampleService exampleService;
    
    @InjectMocks
    private IDEController controller;
    
    @Test
    @DisplayName("Deve carregar a página inicial com o exemplo")
    public void testIndexPage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        
        when(exampleService.getExample("Olá Mundo")).thenReturn("exemplo");
        
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("ide"))
               .andExpect(model().attributeExists("sourceCode"));
    }

    @Test
    @DisplayName("Deve compilar código com sucesso")
    public void testSuccessfulCompilation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        
        String sourceCode = """
            algoritmo "teste"
            var
                inteiro x
            inicio
                x = 10
            fimalgoritmo
            """;

        CompilationResult mockResult = CompilationResult.builder()
            .success(true)
            .output("Compilação realizada com sucesso!")
            .parseTree("(prog algoritmo teste var ...)")
            .build();

        when(compilerService.compile(sourceCode)).thenReturn(mockResult);

        mockMvc.perform(post("/compile")
               .contentType(MediaType.TEXT_PLAIN)
               .content(sourceCode))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.output").value("Compilação realizada com sucesso!"))
               .andExpect(jsonPath("$.parseTree").exists());
    }

    @Test
    @DisplayName("Deve retornar erro para código inválido")
    public void testFailedCompilation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        
        String invalidCode = "código inválido";

        CompilationResult mockResult = CompilationResult.builder()
            .success(false)
            .error("Erro de compilação: Sintaxe inválida")
            .build();

        when(compilerService.compile(invalidCode)).thenReturn(mockResult);

        mockMvc.perform(post("/compile")
               .contentType(MediaType.TEXT_PLAIN)
               .content(invalidCode))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(false))
               .andExpect(jsonPath("$.error").value("Erro de compilação: Sintaxe inválida"));
    }

    @Test
    @DisplayName("Deve compilar programa complexo")
    public void testComplexProgramCompilation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        
        String sourceCode = """
            algoritmo "complexo"
            var
                inteiro a, b
                real media
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
            fimalgoritmo
            """;

        CompilationResult mockResult = CompilationResult.builder()
            .success(true)
            .output("Compilação realizada com sucesso!")
            .parseTree("(prog algoritmo complexo ...)")
            .build();

        when(compilerService.compile(sourceCode)).thenReturn(mockResult);

        mockMvc.perform(post("/compile")
               .contentType(MediaType.TEXT_PLAIN)
               .content(sourceCode))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value(true))
               .andExpect(jsonPath("$.parseTree").exists());
    }
} 