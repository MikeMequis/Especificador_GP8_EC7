// Generated from EasyLanguage.g4 by ANTLR 4.12.0

package br.edu.cefsa.compiler.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link EasyLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface EasyLanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(EasyLanguageParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(EasyLanguageParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#declaravar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaravar(EasyLanguageParser.DeclaravarContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(EasyLanguageParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#bloco}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloco(EasyLanguageParser.BlocoContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmd(EasyLanguageParser.CmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#cmdleitura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdleitura(EasyLanguageParser.CmdleituraContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#cmdescrita}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdescrita(EasyLanguageParser.CmdescritaContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#cmdattrib}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdattrib(EasyLanguageParser.CmdattribContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#cmdselecao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdselecao(EasyLanguageParser.CmdselecaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#cmdrepeticao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdrepeticao(EasyLanguageParser.CmdrepeticaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(EasyLanguageParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(EasyLanguageParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(EasyLanguageParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link EasyLanguageParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(EasyLanguageParser.AtomContext ctx);
}