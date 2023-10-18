// added by JavaCIP
public interface BioPAXGraph {

    public abstract void recordLayout();

    public abstract void setAsRoot();

    public abstract PathwayHolder getPathway();

    public abstract boolean isMechanistic();

    public abstract Iterable<Object> getEdges();

    public abstract boolean fetchLayout();

    public abstract Iterable<Object> getNodes();
}
