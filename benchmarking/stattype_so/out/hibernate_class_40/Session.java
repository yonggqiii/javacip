// added by JavaCIP
public interface Session {

    public abstract Query getNamedQuery(String arg0);

    public abstract void flush();

    public abstract void close();
}
