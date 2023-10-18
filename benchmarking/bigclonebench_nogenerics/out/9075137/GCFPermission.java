// added by JavaCIP
public interface GCFPermission {

    public abstract int getMinPort();

    public abstract int getMaxPort();

    public abstract boolean impliesByHost(GCFPermission arg0);

    public abstract boolean impliesByPorts(GCFPermission arg0);
}
