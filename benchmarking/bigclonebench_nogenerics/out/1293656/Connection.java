// added by JavaCIP
public interface Connection {

    public abstract void commit();

    public abstract Statement createStatement();

    public abstract void rollback();

    public abstract void setAutoCommit(boolean arg0);

    public abstract void close();
}
