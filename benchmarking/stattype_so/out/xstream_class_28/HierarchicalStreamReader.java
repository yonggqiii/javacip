// added by JavaCIP
public interface HierarchicalStreamReader {

    public abstract void moveUp();

    public abstract boolean getNodeName();

    public abstract void moveDown();

    public abstract boolean hasMoreChildren();

    public abstract int getValue();
}
