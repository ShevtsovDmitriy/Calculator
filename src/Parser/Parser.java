package Parser;

import java.util.Vector;

/**
 * Created by Дмитрий on 01.11.2014.
 */
public class Parser {

    public static enum nodeType{add, sub, mult, div, pow, func, num, expr  }

    String expression = "";
    int pos = 0;

    public Parser(){

    }

    public void parse(String expression) throws Exception {
        expression = expression.replaceAll(" ", "");
        this.expression = expression;
        BracketAnalyzer analyzer = new BracketAnalyzer();
        System.out.print(analyzer.analyze(expression));
        NodePrinter.astNodeToAdvancedDosStringTree(expr());
    }

    private boolean isMatched(String... pattern){
        for (String aPattern : pattern) {
            return expression.indexOf(aPattern, pos) == pos;
        }
        return false;
    }

    private char current() throws Exception {
        if (!isEnd()) {
            return expression.charAt(pos);
        }
        throw new Exception("Index Out Of Bounds in current");
    }

    private String  match(String... str){
        for(String aStr: str) {
            if (isMatched(aStr)) {
                pos += aStr.length();
                return aStr;
            }
        }
        return "";
    }

    private boolean isEnd(){
        return pos == expression.length() - 1;
    }

    private void next(){
        if (!isEnd()){
            pos++;
        }
    }

    private Node number() throws Exception {
        System.out.print("\n in number \n");
        String number = "";
        while (Character.isDigit(current())){
            number += current();
            next();

        }
        System.out.print("\n"+number + " is current number");
        if (number.equals("")){
            throw new Exception("Number expected");
        }
        return new Node(nodeType.num, number);
    }

    private Node block() throws Exception {
        System.out.print("\n in block \n");
        if (isMatched("(")){
            match("(");
            System.out.print("\n in block, call add \n");
            Node result = add();
            match(")");
            return result;
        }
        return number();
    }

    private Node mult() throws Exception {
        System.out.print("\n in mult, call block \n");
        Node result = block();
        while (isMatched("*", "/")){
            String operation = match("*", "/");
            System.out.print("\n in mult, call mult \n");
            Node tmp = mult();
            result = operation.equals("*") ? new Node(nodeType.mult, result, tmp)
                    :new Node(nodeType.div, result, tmp);
        }
        return result;
    }

    private Node add() throws Exception {
        System.out.print("\n in add, call mult_1 \n");
        Node result = mult();
        while (isMatched("+", "-")){
            String operation = match("+", "-");
            System.out.print("\n in expr, call mult_2 \n");
            Node tmp = mult();
            result = operation.equals("+") ? new Node(nodeType.add, result, tmp)
                    :new Node(nodeType.sub, result, tmp);
        }
        return result;
    }

    private Node expr() throws Exception {
        System.out.print("\n in expr \n");
        Node top = new Node(nodeType.expr);
        while (!isEnd()){
            System.out.print("\n in expr, call add \n");
            top.addChild(add());
        }
        return top;
    }


}
