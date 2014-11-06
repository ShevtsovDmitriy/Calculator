package Parser;

/**
 * Created by Дмитрий on 01.11.2014.
 */
public class Parser {

    public static enum nodeType{add, sub, mult, div, pow, func, num, block }

    String expression = "";
    int pos = 0;

    public Parser(){

    }

    public void parse(String expression){
        expression = expression.replaceAll(" ", "");
        this.expression = expression;
        BracketAnalyzer analyzer = new BracketAnalyzer();
        System.out.print(analyzer.analyze(expression));
    }

    private boolean isMatched(String pattern){
        return expression.indexOf(pattern, pos) == pos;
    }

    private char current(){
        return expression.charAt(pos);
    }

    private void match(String str){
        if (isMatched(str)) {
            pos += str.length();
        }
    }

    private boolean isEnd(){
        return pos == expression.length();
    }

    private void next(){
        if (!isEnd()){
            pos++;
        }
    }

    private Node number() throws Exception {
        String number = "";
        while (Character.isDigit(current())){
            number += current();
            next();
        }
        if (number.equals("")){
            throw new Exception("Ожидалось число");
        }
        Node result = new Node(nodeType.num, number);
        return result;
    }

    private Node block() throws Exception {

        if (isMatched("(")){
            match("(");
            Node result = block();
            match(")");
        }
        return number();
    }

    private Node mult() throws Exception {
        Node left = number();
        return left;
    }

}
