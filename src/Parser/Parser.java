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
        head = expression(head);

    }

    private boolean isMatched(String... pattern){
        for (String aPattern : pattern) {
            return expression.indexOf(aPattern, pos) == pos;
        }
        return false;
    }

    private char current(){
        if (!isEnd()) {
            return expression.charAt(pos);
        }
        System.out.print("Index Out Of Bounds in current, pos = " + pos);
        return '0';
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
        return pos == expression.length() ;
    }

    private void next(){
        if (!isEnd()){
            pos++;
        }
    }

    private Node num(boolean negative) throws Exception {
        String number = "";
        if (negative) number += "-";
        while (!isEnd() && Character.isDigit(current())){
            number += current();
            next();
        }
        if (number.equals("")){
            System.out.print("Number expected");
        }
        return new Node(nodeType.num, number);
    }

    private Node mult(Node parent) throws Exception {
        boolean negative = false;
        if (!isEnd() && isMatched("(")){
            match("(");
            expression(parent);
            if (isMatched(")"))
                match(")");
            else System.out.print("Bounds exception");
        }
        else {
            if (!isEnd() && isMatched("-"))
            {
                negative = true;
                match("-");
            }
            Node num = num(negative);
            if (!isMatched("*", "/")) parent = num;
            else {
                while (isMatched("*", "/")) {
                    Node tmp = num;
                    if (isMatched("*")) parent = new Node(nodeType.mult, match("*"), tmp);
                    else parent = new Node(nodeType.div, match("/"), tmp);
                    tmp = mult(null);
                    parent.addChild(tmp);
                }
            }
        }

        return parent;
    }

    private Node summ(Node parent) throws Exception {
        if (parent == null) parent = mult(null);
        if (!isEnd() && !isMatched(")")){
            if (isMatched("+", "-")) {
                Node tmp = parent;
                if (isMatched("+")) {
                    parent = new Node(nodeType.add, match("+"),tmp);
                } else {
                    parent = new Node(nodeType.sub, match("-"),tmp);
                }
            }
            else {System.out.print("Some wrong");}
            if (!isEnd() && !isMatched(")")){
                Node tmp = new Node(nodeType.num);
                tmp = mult(tmp);
                if (parent != null) {
                    parent.addChild(tmp);
                }
            }
            else {System.out.print("Unexpected end of line");}
        }
        return parent;
    }

    private Node expression(Node parent) throws Exception {
        while (!isEnd()){
            parent = summ(parent);
        }
        return parent;
    }


}
