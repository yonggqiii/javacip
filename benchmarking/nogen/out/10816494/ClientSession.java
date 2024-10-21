// added by JavaCIP
public interface ClientSession {

    public abstract HeaderSet disconnect(Object arg0);

    public abstract HeaderSet createHeaderSet();

    public abstract HeaderSet connect(HeaderSet arg0);

    public abstract Operation put(HeaderSet arg0);

    public abstract void close();
}
