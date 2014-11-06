package Parser;

/**
 * Created by Дмитрий on 06.11.2014.
 */
public class NodePrinter {

    public static final char ConnectChar    = '|',
                                MiddleNodeChar = '*',
                                LastNodeChar   = '-';

    private static String getStringSubTree(Node node, String indent, boolean root) {
        if (node == null)
            return "";

        String result = indent;
        if (!root)
            if(node.childCount() < 2) {
                result += MiddleNodeChar + " ";
                indent += ConnectChar + " ";
            }
            else {
                result += LastNodeChar + " ";
                indent += "  ";
            }
        result += node + "\n";
        for(int i = 0; i < node.childCount(); i++)
            result += getStringSubTree(node.getGhidren(i), indent, false);

        return result;
    }

    public static String astNodeToAdvancedDosStringTree(Node node) {
        return getStringSubTree(node, "", true);
    }

    public static void Print(Node node) {
        String tree = astNodeToAdvancedDosStringTree(node);
        System.out.print(tree);
    }

}
