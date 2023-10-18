// added by JavaCIP
public interface Element {

    public abstract void appendChild(Element arg0);

    public abstract Object getLocalName();

    public abstract SVGDocument getOwnerDocument();

    public abstract Node getFirstChild();

    public abstract Object getNamespaceURI();

    public abstract void setAttributeNS(boolean arg0, String arg1, String arg2);
}
