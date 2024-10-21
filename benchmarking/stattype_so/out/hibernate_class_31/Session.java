// added by JavaCIP
public interface Session {

    public abstract UNKNOWN_49 getTransaction();

    public abstract void beginTransaction();

    public abstract Query createQuery(String arg0);

    public abstract void flush();

    public abstract void close();
}
