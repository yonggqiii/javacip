// added by JavaCIP
public interface Connection {

    public abstract Statement createStatement();

    public abstract void commit();

    public abstract void close();

    public abstract void rollback();
}