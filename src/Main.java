import Parser.*;

/**
 * Created by Дмитрий on 06.11.2014.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        //Parser parser = new Parser();
        //parser.parse("2+3");

        Node nd = new Node(Parser.nodeType.num);
        some(nd);
        System.out.print(nd.getType());


    }
    public static void some(Node node){
        System.out.print(node.getType());
        node = new Node(Parser.nodeType.add);
        System.out.print(node.getType());
    }
}
