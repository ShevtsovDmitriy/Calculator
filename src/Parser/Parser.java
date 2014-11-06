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

    private char current(){
        return expression.charAt(pos);
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
        return pos == expression.length();
    }

    private void next(){
        if (!isEnd()){
            pos++;
            System.out.print("\nit is not end");
        }
    }

    private Node number() throws Exception {
        String number = "";
        while (Character.isDigit(current())){
            number += current();
            next();

        }
        System.out.print("\n"+number + " is current number");
        if (number.equals("")){
            System.out.print("\n" + number + " number not found");
            throw new Exception("Number expected");
        }
        return new Node(nodeType.num, number);
    }

    private Node block() throws Exception {

        if (isMatched("(")){
            match("(");
            Node result = add();
            match(")");
            return result;
        }
        return number();
    }

    private Node mult() throws Exception {
        return block();
    }

    private Node add() throws Exception {
        Node result = mult();
        while (isMatched("+", "-")){
            String operation = match("+", "-");
            Node tmp = mult();
            result = operation=="+" ? new Node(nodeType.add, result, tmp)
                    :new Node(nodeType.sub, result, tmp);
        }

        return result;
    }

    private Node expr() throws Exception {
        Node top = new Node(nodeType.expr);
        while (!isEnd()){
            top.addChild(add());
        }
        return top;
    }


}
