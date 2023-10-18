// added by JavaCIP
public interface PathObject {

    public abstract boolean isFetched();

    public abstract List<PathObject> getChildren();

    public abstract Object getType();

    public abstract double getPath();
}
