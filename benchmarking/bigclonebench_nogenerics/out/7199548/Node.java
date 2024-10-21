// added by JavaCIP
public interface Node extends Element {

    public static boolean ELEMENT_NODE = false;

    public abstract Node getNextSibling();

    public abstract boolean getNodeType();

    public abstract Object getNamespaceURI();

    public abstract Object getLocalName();
}
