package br.edu.cefsa.compiler.semantic;

import br.edu.cefsa.compiler.datastructures.EasySymbolTable;
import br.edu.cefsa.compiler.datastructures.EasyVariable;
import br.edu.cefsa.compiler.datastructures.EasySymbol;
import br.edu.cefsa.compiler.exceptions.EasySemanticException;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class EasySemanticAnalyzer {
    private EasySymbolTable symbolTable;
    private Stack<EasySymbolTable> scopeStack;
    private Map<String, Object> constantValues;
    private Set<String> initializedVariables;
    private boolean inLoop;
    private int currentLine;
    private int currentColumn;

    public EasySemanticAnalyzer() {
        this.symbolTable = new EasySymbolTable();
        this.scopeStack = new Stack<>();
        this.scopeStack.push(symbolTable);
        this.constantValues = new HashMap<>();
        this.initializedVariables = new HashSet<>();
        this.inLoop = false;
        this.currentLine = 0;
        this.currentColumn = 0;
    }

    public void setPosition(int line, int column) {
        this.currentLine = line;
        this.currentColumn = column;
    }

    // Scope Management
    public void enterScope() {
        EasySymbolTable newScope = new EasySymbolTable();
        scopeStack.push(newScope);
        symbolTable = newScope;
    }

    public void exitScope() {
        if (scopeStack.size() > 1) {
            // Check for uninitialized variables in the scope before exiting
            checkUninitializedVariables();
            scopeStack.pop();
            symbolTable = scopeStack.peek();
        }
    }

    // Variable Management
    public void declareVariable(String id, int type, String value) {
        if (symbolTable.exists(id)) {
            throwError("Variable '" + id + "' already declared in current scope");
        }
        EasyVariable var = new EasyVariable(id, type, value);
        symbolTable.add(var);
        
        // If initial value provided, mark as initialized
        if (value != null) {
            initializedVariables.add(id);
            constantValues.put(id, parseConstantValue(value, type));
        }
    }

    public void markInitialized(String id) {
        if (!symbolTable.exists(id)) {
            throwError("Cannot initialize undeclared variable '" + id + "'");
        }
        initializedVariables.add(id);
    }

    public boolean isInitialized(String id) {
        return initializedVariables.contains(id);
    }

    public EasyVariable resolveVariable(String id) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            EasySymbolTable scope = scopeStack.get(i);
            if (scope.exists(id)) {
                return (EasyVariable) scope.get(id);
            }
        }
        throwError("Variable '" + id + "' not declared");
        return null;
    }

    // Control Flow Validation
    public void enterLoop() {
        inLoop = true;
    }

    public void exitLoop() {
        inLoop = false;
    }

    public boolean isInLoop() {
        return inLoop;
    }

    // Expression Validation and Type Checking
    public void validateExpression(String id) {
        EasyVariable var = resolveVariable(id);
        if (!isInitialized(id)) {
            throwError("Variable '" + id + "' may not have been initialized");
        }
    }

    public void validateCondition(String id) {
        validateExpression(id);
        EasyVariable var = resolveVariable(id);
        if (var.getType() != EasyVariable.BOOLEAN) {
            throwError("Condition must be boolean, found: " + typeToString(var.getType()));
        }
    }

    public int validateAssignment(String targetId, String expression, int expressionType) {
        EasyVariable target = resolveVariable(targetId);
        
        // Check if types are compatible
        if (!canConvert(expressionType, target.getType())) {
            throwError("Cannot assign " + typeToString(expressionType) + 
                      " to " + typeToString(target.getType()));
        }

        // Mark target as initialized
        markInitialized(targetId);
        
        return target.getType();
    }

    // Expression Type Inference
    public int inferExpressionType(String operator, int leftType, int rightType) {
        // Handle unary operators
        if (rightType == -1) {
            switch (operator) {
                case "nao":
                    if (leftType != EasyVariable.BOOLEAN) {
                        throwError("Operator 'nao' requires boolean operand");
                    }
                    return EasyVariable.BOOLEAN;
                default:
                    return leftType;
            }
        }

        // Handle binary operators
        switch (operator) {
            case "+":
                if (leftType == EasyVariable.TEXT || rightType == EasyVariable.TEXT) {
                    return EasyVariable.TEXT;
                }
                return inferArithmeticType(operator, leftType, rightType);
            
            case "-":
            case "*":
            case "/":
                return inferArithmeticType(operator, leftType, rightType);
            
            case "=":
            case "<>":
            case "<":
            case ">":
            case "<=":
            case ">=":
                validateComparisonTypes(leftType, rightType);
                return EasyVariable.BOOLEAN;
            
            case "e":
            case "ou":
                validateLogicalTypes(leftType, rightType);
                return EasyVariable.BOOLEAN;
            
            default:
                throwError("Unknown operator: " + operator);
                return -1;
        }
    }

    private int inferArithmeticType(String operator, int leftType, int rightType) {
        validateNumericTypes(leftType, rightType);
        
        if (operator.equals("/")) {
            return EasyVariable.REAL;
        }
        
        return (leftType == EasyVariable.REAL || rightType == EasyVariable.REAL) ? 
               EasyVariable.REAL : EasyVariable.INTEGER;
    }

    // Type Validation
    private void validateNumericTypes(int leftType, int rightType) {
        if (!isNumeric(leftType) || !isNumeric(rightType)) {
            throwError("Arithmetic operators require numeric operands");
        }
    }

    private void validateLogicalTypes(int leftType, int rightType) {
        if (leftType != EasyVariable.BOOLEAN || rightType != EasyVariable.BOOLEAN) {
            throwError("Logical operators require boolean operands");
        }
    }

    private void validateComparisonTypes(int leftType, int rightType) {
        if (!canCompare(leftType, rightType)) {
            throwError("Cannot compare types: " + 
                      typeToString(leftType) + " and " + typeToString(rightType));
        }
    }

    // Utility Methods
    private void checkUninitializedVariables() {
        for (EasySymbol symbol : symbolTable.getAll()) {
            if (!initializedVariables.contains(symbol.getName())) {
                // Just warn about uninitialized variables
                System.err.println("Warning: Variable '" + symbol.getName() + 
                                 "' may not have been initialized");
            }
        }
    }

    private boolean canCompare(int type1, int type2) {
        if (type1 == type2) return true;
        if (isNumeric(type1) && isNumeric(type2)) return true;
        return false;
    }

    private boolean isNumeric(int type) {
        return type == EasyVariable.INTEGER || type == EasyVariable.REAL;
    }

    private Object parseConstantValue(String value, int type) {
        try {
            switch (type) {
                case EasyVariable.INTEGER:
                    return Integer.parseInt(value);
                case EasyVariable.REAL:
                    return Double.parseDouble(value);
                case EasyVariable.BOOLEAN:
                    return Boolean.parseBoolean(value);
                case EasyVariable.TEXT:
                    return value;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            throwError("Invalid constant value '" + value + "' for type " + typeToString(type));
            return null;
        }
    }

    public boolean canConvert(int fromType, int toType) {
        if (fromType == toType) return true;
        if (fromType == EasyVariable.INTEGER && toType == EasyVariable.REAL) return true;
        if (toType == EasyVariable.TEXT) return true;
        return false;
    }

    private String typeToString(int type) {
        switch (type) {
            case EasyVariable.INTEGER: return "inteiro";
            case EasyVariable.REAL: return "real";
            case EasyVariable.TEXT: return "literal";
            case EasyVariable.BOOLEAN: return "logico";
            default: return "unknown";
        }
    }

    private void throwError(String message) {
        throw new EasySemanticException(
            new EasySemanticError(currentLine, currentColumn, message, "")
        );
    }

    // Getters
    public EasySymbolTable getCurrentScope() {
        return symbolTable;
    }

    public Object getConstantValue(String id) {
        return constantValues.get(id);
    }
} 