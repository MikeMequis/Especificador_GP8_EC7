# IDE Online EasyLanguage

Uma IDE web para a linguagem educacional EasyLanguage, desenvolvida como parte do projeto da disciplina EC7 no Centro Universitário FESA.

## 📋 Sobre o Projeto

Este projeto implementa uma IDE online para a linguagem EasyLanguage, uma linguagem de programação educacional com sintaxe em português. A IDE oferece um ambiente de desenvolvimento integrado com recursos essenciais para aprendizado de programação.

### 🌟 Características Principais

- Editor de código com destaque de sintaxe personalizado
- Compilação em tempo real
- Visualização da árvore sintática
- Interface web moderna e responsiva
- Exemplos de código prontos para uso
- Suporte completo à sintaxe EasyLanguage
- Interface totalmente em português

## 🛠️ Tecnologias Utilizadas

- **Backend:**
  - Spring Boot 3.5.0
  - ANTLR4 4.12.0
  - Java 17
  - Maven

- **Frontend:**
  - Thymeleaf
  - Bootstrap 5
  - CodeMirror (editor de código)
  - JavaScript

## 📦 Estrutura do Projeto

### IDE Web (especificador_gp8_ec7)
```
especificador_gp8_ec7/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/fesa/gp8/especificador_gp8_ec7/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
└── pom.xml
```

### Compilador (EasyLanguageFinal)
```
EasyLanguageFinal/
├── src/
│   └── main/
│       └── java/
│           └── br/edu/cefsa/compiler/
│               ├── parser/
│               │   ├── EasyLanguageLexer.java     # Analisador léxico
│               │   └── EasyLanguageParser.java     # Analisador sintático
│               ├── semantic/
│               │   ├── EasySemanticAnalyzer.java  # Analisador semântico
│               │   └── EasySemanticError.java     # Tratamento de erros semânticos
│               ├── abstractsyntaxtree/            # Árvore Sintática Abstrata (AST)
│               │   ├── AbstractCommand.java       # Classe base para comandos
│               │   ├── CommandAtribuicao.java    # Comando de atribuição
│               │   ├── CommandDecisao.java       # Estruturas condicionais
│               │   ├── CommandEscrita.java       # Comandos de saída
│               │   ├── CommandLeitura.java       # Comandos de entrada
│               │   ├── CommandRepeticao.java     # Estruturas de repetição
│               │   └── EasyProgram.java          # Representação do programa
│               ├── exceptions/                    # Tratamento de exceções
│               ├── datastructures/               # Estruturas de dados auxiliares
│               └── main/                         # Ponto de entrada do compilador
├── resources/
│   ├── EasyLanguage.g4                          # Gramática ANTLR4
│   ├── input.easy                               # Arquivo de entrada de exemplo
│   └── antlr4-4.12.0.jar                       # Dependência ANTLR4
└── pom.xml                                      # Dependências Maven
```

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 17 ou superior
- Maven 3.6 ou superior
- Git

### Passos para Execução

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/Especificador_GP8_EC7.git
```

2. Entre no diretório do projeto:
```bash
cd Especificador_GP8_EC7
```

3. Compile e execute:
```bash
mvn spring-boot:run
```

4. Acesse a IDE no navegador:
```
http://localhost:8080
```

## 📝 Exemplos de Código

A IDE inclui vários exemplos prontos para uso:

1. **Olá Mundo** - Introdução básica
2. **Calculadora Simples** - Operações matemáticas
3. **Média de Notas** - Estruturas condicionais
4. **Contador com Loop** - Estruturas de repetição
5. **Manipulação de Strings** - Operações com texto

## 🔍 Funcionalidades da IDE

### Editor de Código
- Destaque de sintaxe personalizado
- Numeração de linhas
- Indentação automática
- Tema escuro para melhor visualização

### Compilador
- Análise sintática em tempo real
- Mensagens de erro detalhadas
- Visualização da árvore sintática
- Feedback imediato

### Interface
- Design responsivo
- Seleção de exemplos
- Área de saída separada
- Visualização da árvore sintática

## 🧪 Testes

O projeto inclui testes unitários abrangentes:

```bash
mvn test
```

Cobertura de testes:
- Testes do compilador
- Testes da interface web
- Testes dos exemplos
- Testes de integração

## 📚 Sintaxe da Linguagem

### Estrutura Básica
```
algoritmo "nome_do_programa"
var
    // declarações de variáveis
inicio
    // comandos
fimalgoritmo
```

### Tipos de Dados
- `inteiro`: Números inteiros
- `real`: Números decimais
- `literal`: Textos e strings
- `logico`: Valores lógicos (verdadeiro/falso)

### Comandos Principais
- Entrada: `leia(variavel)`
- Saída: `escreva(expressao)` ou `escreval(expressao)`
- Condicionais: `se`, `entao`, `senao`, `fimse`
- Repetição: `enquanto`, `faca`, `fimenquanto`

## 📖 Gramática da Linguagem

### Estrutura Léxica

#### Palavras-Chave
```
algoritmo    var         inicio      fimalgoritmo
se          entao       senao       fimse
enquanto    faca        fimenquanto
inteiro     real        literal     logico
verdadeiro  falso       e           ou          nao
leia        escreva     escreval
```

#### Operadores
```
Aritméticos:  +  -  *  /
Relacionais:  >  <  >=  <=  =  <>
Lógicos:      e  ou  nao
```

#### Tokens Especiais
```
Parênteses:     (  )
Aspas:          "  "
Vírgula:        ,
```

### Regras Sintáticas

#### Programa
```
programa → 'algoritmo' STRING
           declaracao_variaveis?
           'inicio'
           lista_comandos
           'fimalgoritmo'
```

#### Declaração de Variáveis
```
declaracao_variaveis → 'var' (declaracao)*
declaracao → tipo lista_identificadores
tipo → 'inteiro' | 'real' | 'literal' | 'logico'
lista_identificadores → IDENTIFICADOR (',' IDENTIFICADOR)*
```

#### Comandos
```
comando → atribuicao
       | leitura
       | escrita
       | condicional
       | repeticao

atribuicao → IDENTIFICADOR '=' expressao

leitura → 'leia' '(' IDENTIFICADOR ')'

escrita → ('escreva' | 'escreval') '(' expressao ')'

condicional → 'se' expressao 'entao' 
              lista_comandos 
              ('senao' lista_comandos)? 
              'fimse'

repeticao → 'enquanto' expressao 'faca' 
            lista_comandos 
            'fimenquanto'
```

#### Expressões
```
expressao → expressao_simples (op_relacional expressao_simples)?

expressao_simples → termo (op_aditivo termo)*

termo → fator (op_multiplicativo fator)*

fator → IDENTIFICADOR
      | NUMERO
      | STRING
      | 'verdadeiro'
      | 'falso'
      | '(' expressao ')'
      | 'nao' fator

op_relacional → '>' | '<' | '>=' | '<=' | '=' | '<>'
op_aditivo → '+' | '-' | 'ou'
op_multiplicativo → '*' | '/' | 'e'
```

### Regras Semânticas

1. **Tipagem**
   - Tipagem estática
   - Verificação de tipos em operações
   - Conversão implícita limitada

2. **Escopo**
   - Escopo global para variáveis
   - Não permite redeclaração de variáveis

3. **Operações**
   - Operações aritméticas: apenas com `inteiro` e `real`
   - Operações lógicas: apenas com `logico`
   - Concatenação: permitida com `literal`

4. **Compatibilidade de Tipos**
```
+----------------+----------+--------+---------+--------+
| Operação       | inteiro  | real   | literal | logico |
+----------------+----------+--------+---------+--------+
| +, -, *, /     |    ✓     |   ✓    |    ✗    |   ✗    |
| >, <, >=, <=   |    ✓     |   ✓    |    ✗    |   ✗    |
| =, <>          |    ✓     |   ✓    |    ✓    |   ✓    |
| e, ou          |    ✗     |   ✗    |    ✗    |   ✓    |
| nao            |    ✗     |   ✗    |    ✗    |   ✓    |
+----------------+----------+--------+---------+--------+
```

### Exemplos de Uso

#### 1. Expressões Aritméticas
```
algoritmo "calculos"
var
    inteiro a, b, soma
    real divisao
inicio
    a = 10
    b = 5
    soma = a + b          // Resultado: 15
    divisao = a / b       // Resultado: 2.0
fimalgoritmo
```

#### 2. Expressões Lógicas
```
algoritmo "logica"
var
    logico x, y, resultado
inicio
    x = verdadeiro
    y = falso
    resultado = x e y     // Resultado: falso
    resultado = x ou y    // Resultado: verdadeiro
    resultado = nao x     // Resultado: falso
fimalgoritmo
```