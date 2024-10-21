// added by JavaCIP
public interface Session {

    public abstract Transaction beginTransaction();

    public abstract Query createQuery(String arg0);

    public abstract void close();
}
