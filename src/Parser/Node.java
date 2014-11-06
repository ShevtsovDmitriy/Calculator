package Parser;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Дмитрий on 01.11.2014.
 */
public class Node {

    private Parser.nodeType type;
    private String value;
    private ArrayList<Node> children = new ArrayList<Node>();

    public Node(Parser.nodeType nodeType, Node... child){
        this.type = nodeType;
        if (nodeType == Parser.nodeType.add || nodeType == Parser.nodeType.div || nodeType == Parser.nodeType.mult
                || nodeType == Parser.nodeType.func || nodeType == Parser.nodeType.sub){
            value = nodeType.name();
        }
        Collections.addAll(children, child);
    }

    public Node(Parser.nodeType nodeType, String value, Node... child){
        this.type = nodeType;
        this.value = value;
        Collections.addAll(children, child);
    }

    public void addChild(Node child){
        if (child != null){
            children.add(child);
        }
    }

    public Node getGhidren(int index){
        if (children.size() > index){
            return children.get(index);
        }
        return null;
    }

    public int childCount(){
        return children.size();
    }
}
