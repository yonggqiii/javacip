// added by JavaCIP
public class Node implements Element {

    public static boolean ELEMENT_NODE;

    public boolean getNodeType() {
        return false;
    }

    public boolean getNodeValue() {
        return false;
    }

    public NodeList getElementsByTagName(String arg0) {
        return null;
    }

    public NodeList getChildNodes() {
        return null;
    }

    Node() {
        super();
    }
}
