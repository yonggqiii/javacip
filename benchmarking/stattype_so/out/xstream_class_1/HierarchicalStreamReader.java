// added by JavaCIP
public interface HierarchicalStreamReader {

    public abstract boolean hasMoreChildren();

    public abstract void moveDown();

    public abstract Object getNodeName();

    public abstract String getValue();
}
