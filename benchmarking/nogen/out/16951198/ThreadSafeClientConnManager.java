// added by JavaCIP
public interface ThreadSafeClientConnManager {

    public abstract boolean getConnectionsInPool();

    public abstract ClientConnectionRequest requestConnection(HttpRoute arg0, Object arg1);

    public abstract void releaseConnection(ManagedClientConnection arg0, int arg1, Object arg2);

    public abstract void shutdown();
}
