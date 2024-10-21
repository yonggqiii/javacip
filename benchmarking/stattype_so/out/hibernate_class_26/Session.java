// added by JavaCIP
public interface Session {

    public abstract void beginTransaction();

    public abstract UNKNOWN_82 getTransaction();

    public abstract Query createQuery(String arg0);

    public abstract void save(A arg0);
}
