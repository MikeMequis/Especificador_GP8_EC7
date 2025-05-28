package com.fesa.gp8.especificador_gp8_ec7.service;

import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ExampleService {
    
    private final Map<String, String> examples;
    
    public ExampleService() {
        examples = new LinkedHashMap<>();
        
        examples.put("Olá Mundo", """
            algoritmo "ola_mundo"
            var
                literal mensagem
            inicio
                mensagem = "Olá, Mundo!"
                escreval(mensagem)
            fimalgoritmo
            """);
            
        examples.put("Calculadora Simples", """
            algoritmo "calculadora"
            var
                inteiro a, b, resultado
                literal operacao
            inicio
                escreval("Digite o primeiro número:")
                leia(a)
                escreval("Digite o segundo número:")
                leia(b)
                escreval("Digite a operação (+, -, *, /):")
                leia(operacao)
                
                se operacao = "+" entao
                    resultado = a + b
                senao
                    se operacao = "-" entao
                        resultado = a - b
                    senao
                        se operacao = "*" entao
                            resultado = a * b
                        senao
                            se operacao = "/" entao
                                resultado = a / b
                            fimse
                        fimse
                    fimse
                fimse
                
                escreval("Resultado: ", resultado)
            fimalgoritmo
            """);
            
        examples.put("Média de Notas", """
            algoritmo "media_notas"
            var
                real nota1, nota2, media
                logico aprovado
            inicio
                escreval("Digite a primeira nota:")
                leia(nota1)
                escreval("Digite a segunda nota:")
                leia(nota2)
                
                media = (nota1 + nota2) / 2
                
                se media >= 6 entao
                    aprovado = verdadeiro
                    escreval("Aprovado com média ", media)
                senao
                    aprovado = falso
                    escreval("Reprovado com média ", media)
                fimse
            fimalgoritmo
            """);
            
        examples.put("Contador com Loop", """
            algoritmo "contador"
            var
                inteiro i, limite
            inicio
                escreval("Digite o limite do contador:")
                leia(limite)
                
                i = 1
                enquanto i <= limite faca
                    escreval("Número: ", i)
                    i = i + 1
                fimenquanto
            fimalgoritmo
            """);
            
        examples.put("Manipulação de Strings", """
            algoritmo "strings"
            var
                literal nome, sobrenome, nomeCompleto
            inicio
                escreval("Digite seu nome:")
                leia(nome)
                escreval("Digite seu sobrenome:")
                leia(sobrenome)
                
                nomeCompleto = nome + " " + sobrenome
                
                escreval("Nome completo: ", nomeCompleto)
                escreval("Tamanho do nome: ", nomeCompleto)
            fimalgoritmo
            """);
    }
    
    public Map<String, String> getExamples() {
        return examples;
    }
    
    public String getExample(String name) {
        return examples.getOrDefault(name, "");
    }
} 