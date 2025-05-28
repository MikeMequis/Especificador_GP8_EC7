package br.edu.cefsa.compiler.datastructures;

public class EasyVariable extends EasySymbol {

    public static final int INTEGER = 0;
    public static final int REAL = 1;
    public static final int TEXT = 2;
    public static final int BOOLEAN = 3;

    private int type;
    private String value;

    public EasyVariable(String name, int type, String value) {
        super(name);
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EasyVariable [name=" + name + ", type=" + type + ", value=" + value + "]";
    }

    public String generateJavaCode() {
        String str;
        switch (type) {
            case INTEGER:
                str = "int";
                break;
            case REAL:
                str = "double";
                break;
            case TEXT:
                str = "String";
                break;
            case BOOLEAN:
                str = "boolean";
                break;
            default:
                str = "Object";
        }
        return str + " " + super.name + ";";
    }

}
