package Parser;

/**
 * Created by Дмитрий on 01.11.2014.
 * String parser. Can parse math expressions
 */
public class Parser {

    public static enum nodeType{add, sub, mult, div, pow, func, num, expr  }

    String expression = "";
    int pos = 0;
    Node head;

    public Parser(){

    }

    public void parse(String expression) throws Exception {
        expression = expression.replaceAll(" ", "");
        this.expression = expression;
        BracketAnalyzer analyzer = new BracketAnalyzer();
        System.out.print(analyzer.analyze(expression));

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
        throw new Exception("Index Out Of Bounds in current, pos = " + pos);
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

    private Node num(Node parent){

    }

    private Node mult(Node parent){
        boolean negative = false;
        if (!isEnd() && isMatched("(")){
            match("(");
            expression(parent);
            if (isMatched(")"))
                match(")");
            else System.out.print("Что-то не так со скобками");
        }
        else {
            if (!isEnd() && isMatched("-"))
            {
                negative = true;
                match("-");
            }

        }


    }

    private Node summ(Node parent){
        if (parent == null) parent = mult(parent);
        if (!isEnd() && !isMatched(")")){
            if (isMatched("+", "-")) {
                Node tmp = parent;
                if (isMatched("+")) {
                    parent = new Node(nodeType.add, match("+"),tmp);
                } else {
                    parent = new Node(nodeType.sub, match("-"),tmp);
                }
            }
            else {System.out.print("Что-то не так");}
            if (!isEnd() && !isMatched(")")){
                Node tmp = new Node(nodeType.num);
                tmp = mult(tmp);
                if (parent != null) {
                    parent.addChild(tmp);
                }
            }
            else {System.out.print("Неожиданный конец строки");}
        }
        return parent;
    }

    private Node expression(Node parent){
        while (!isEnd()){
            parent = summ(parent);
        }
        return parent;
    }


}
