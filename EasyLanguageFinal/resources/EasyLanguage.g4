grammar EasyLanguage;

options {
    language = Java;
}

@header {
package br.edu.cefsa.compiler.parser;

import br.edu.cefsa.compiler.datastructures.EasySymbol;
import br.edu.cefsa.compiler.datastructures.EasyVariable;
import br.edu.cefsa.compiler.datastructures.EasySymbolTable;
import br.edu.cefsa.compiler.exceptions.EasySemanticException;
import br.edu.cefsa.compiler.abstractsyntaxtree.EasyProgram;
import br.edu.cefsa.compiler.abstractsyntaxtree.AbstractCommand;
import br.edu.cefsa.compiler.abstractsyntaxtree.CommandLeitura;
import br.edu.cefsa.compiler.abstractsyntaxtree.CommandEscrita;
import br.edu.cefsa.compiler.abstractsyntaxtree.CommandAtribuicao;
import br.edu.cefsa.compiler.abstractsyntaxtree.CommandDecisao;
import br.edu.cefsa.compiler.abstractsyntaxtree.CommandRepeticao;
import java.util.ArrayList;
import java.util.Stack;
}

@members {
	private int _tipo;
	private String _varName;
	private String _varValue;
	private EasySymbolTable symbolTable = new EasySymbolTable();
	private EasySymbol symbol;
	private EasyProgram program = new EasyProgram();
	private ArrayList<AbstractCommand> curThread;
	private Stack<ArrayList<AbstractCommand>> stack = new Stack<ArrayList<AbstractCommand>>();
	private String _readID;
	private String _writeID;
	private String _exprID;
	private String _exprContent;
	private String _exprDecision;
	private ArrayList<AbstractCommand> listaTrue;
	private ArrayList<AbstractCommand> listaFalse;
	
	public void verificaID(String id){
		if (!symbolTable.exists(id)){
			throw new EasySemanticException("Symbol "+id+" not declared");
		}
	}
	
	public void exibeComandos(){
		for (AbstractCommand c: program.getComandos()){
			System.out.println(c);
		}
	}
	
	public void generateCode(){
		program.generateTarget();
	}
}

prog	: 'algoritmo' STRING NEWLINE
          ('var' NEWLINE decl)?
          bloco 
          'fimalgoritmo'
           {  program.setVarTable(symbolTable);
              program.setComandos(stack.pop());
           } 
	;
		
decl    :  (declaravar)*
        ;
        
declaravar :  tipo ID  {
	                  _varName = _input.LT(-1).getText();
	                  _varValue = null;
	                  symbol = new EasyVariable(_varName, _tipo, _varValue);
	                  if (!symbolTable.exists(_varName)){
	                     symbolTable.add(symbol);	
	                  }
	                  else{
	                  	 throw new EasySemanticException("Symbol "+_varName+" already declared");
	                  }
                    } 
              (  VIR 
              	 ID {
	                  _varName = _input.LT(-1).getText();
	                  _varValue = null;
	                  symbol = new EasyVariable(_varName, _tipo, _varValue);
	                  if (!symbolTable.exists(_varName)){
	                     symbolTable.add(symbol);	
	                  }
	                  else{
	                  	 throw new EasySemanticException("Symbol "+_varName+" already declared");
	                  }
                    }
              )* 
               NEWLINE
           ;
           
tipo       : 'inteiro' { _tipo = EasyVariable.INTEGER;  }
           | 'real'    { _tipo = EasyVariable.REAL;     }
           | 'literal' { _tipo = EasyVariable.TEXT;     }
           | 'logico'  { _tipo = EasyVariable.BOOLEAN;  }
           ;
        
bloco	: 'inicio' NEWLINE
          { curThread = new ArrayList<AbstractCommand>(); 
	        stack.push(curThread);  
          }
          (cmd | NEWLINE)*
          ;
		
cmd		:  cmdleitura  
 		|  cmdescrita 
 		|  cmdattrib
 		|  cmdselecao
 		|  cmdrepeticao  
		;
		
cmdleitura	: 'leia' AP ID { verificaID(_input.LT(-1).getText()); } FP NEWLINE
              {
                String id = _input.LT(-3).getText();
                EasyVariable var = (EasyVariable)symbolTable.get(id);
                CommandLeitura cmd = new CommandLeitura(id, var);
                stack.peek().add(cmd);
              }   
			;
			
cmdescrita	: ('escreva' | 'escreval')
                 AP 
                 { _exprContent = ""; }
                 expr
                 FP 
                 NEWLINE
               {
                 CommandEscrita cmd = new CommandEscrita(_exprContent);
                 stack.peek().add(cmd);
               }
			;
			
cmdattrib	:  ID { verificaID(_input.LT(-1).getText());
                    _exprID = _input.LT(-1).getText();
                   } 
               ATTR { _exprContent = ""; } 
               expr 
               NEWLINE
               {
               	 CommandAtribuicao cmd = new CommandAtribuicao(_exprID, _exprContent);
               	 stack.peek().add(cmd);
               }
			;
			
cmdselecao  :  'se' { _exprContent = ""; } 
               expr { _exprDecision = _exprContent; }
               'entao' NEWLINE
                    { curThread = new ArrayList<AbstractCommand>(); 
                      stack.push(curThread);
                    }
                    (cmd | NEWLINE)+ 
                    {
                       listaTrue = stack.pop();	
                    } 
                   ('senao' NEWLINE
                   	{
                   	 	curThread = new ArrayList<AbstractCommand>();
                   	 	stack.push(curThread);
                   	} 
                   	(cmd | NEWLINE)+ 
                   	{
                   		listaFalse = stack.pop();
                   	}
                   )? 
                   'fimse' NEWLINE
                   {
                   		CommandDecisao cmd = new CommandDecisao(_exprDecision, listaTrue, listaFalse);
                   		stack.peek().add(cmd);
                   }
            ;

cmdrepeticao : 'enquanto' { _exprContent = ""; }
               expr { _exprDecision = _exprContent; }
               'faca' NEWLINE
                    { curThread = new ArrayList<AbstractCommand>(); 
                      stack.push(curThread);
                    }
                    (cmd | NEWLINE)+ 
                    'fimenquanto' NEWLINE
                    {
                        ArrayList<AbstractCommand> corpo = stack.pop();
                        CommandRepeticao cmd = new CommandRepeticao(_exprDecision, corpo);
                        stack.peek().add(cmd);
                    }
             ;
			
expr    : term (op=('+'|'-') { _exprContent += $op.text; } term)*
        ;

term    : factor (op=('*'|'/') { _exprContent += $op.text; } factor)*
        ;

factor  : atom (op=('='|'<'|'>'|'<='|'>='|'<>'|'e'|'ou') { _exprContent += $op.text; } atom)*
        ;

atom    : ID { verificaID(_input.LT(-1).getText());
               _exprContent += _input.LT(-1).getText();
             }
        | NUMBER { _exprContent += _input.LT(-1).getText(); }
        | STRING { _exprContent += _input.LT(-1).getText(); }
        | BOOLEAN { _exprContent += _input.LT(-1).getText(); }
        | AP { _exprContent += "("; } 
          expr 
          FP { _exprContent += ")"; }
        | 'nao' { _exprContent += "!"; } atom
        ;

// Lexer rules
STRING : '"' ~["\r\n]* '"' ;
NUMBER : [0-9]+ ('.' [0-9]+)? ;
BOOLEAN : 'verdadeiro' | 'falso' ;
ID : [a-zA-Z][a-zA-Z0-9]* ;
VIR : ',' ;
AP : '(' ;
FP : ')' ;
ATTR : '=' ;
NEWLINE : [\r\n] ;
WS : [ \t]+ -> skip ;