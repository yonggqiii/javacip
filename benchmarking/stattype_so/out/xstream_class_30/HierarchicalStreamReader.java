// added by JavaCIP
public interface HierarchicalStreamReader {

    public abstract void moveUp();

    public abstract String getAttribute(String arg0);

    public abstract void moveDown();

    public abstract boolean hasMoreChildren();

    public abstract String getValue();
}
