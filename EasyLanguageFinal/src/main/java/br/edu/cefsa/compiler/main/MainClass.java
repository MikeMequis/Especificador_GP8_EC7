package br.edu.cefsa.compiler.main;

import br.edu.cefsa.compiler.exceptions.EasySemanticException;
import br.edu.cefsa.compiler.parser.EasyLanguageLexer;
import br.edu.cefsa.compiler.parser.EasyLanguageParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/* esta é a classe que é responsável por criar a interação com o usuário
 * instanciando nosso parser e apontando para o arquivo fonte
 * 
 * Arquivo fonte: extensao .easy
 * 
 */
public class MainClass {

    public static void main(String[] args) {
        try {
            // Create the CharStream from the input file
            CharStream input = CharStreams.fromFileName("./resources/input.easy");
            
            // Create lexer
            EasyLanguageLexer lexer = new EasyLanguageLexer(input);
            
            // Create token stream
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            // Create parser
            EasyLanguageParser parser = new EasyLanguageParser(tokens);
            
            // Parse the input
            EasyLanguageParser.ProgContext tree = parser.prog();
            
            // If we get here, parsing was successful
            System.out.println("Compilation Successful");
            System.out.println("Parse tree: " + tree.toStringTree(parser));
            
        } catch (ParseCancellationException e) {
            System.err.println("Syntax error: " + e.getMessage());
        } catch (EasySemanticException e) {
            System.err.println("Semantic error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
