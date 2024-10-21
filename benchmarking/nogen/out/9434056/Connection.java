// added by JavaCIP
public interface Connection {

    public abstract void setAutoCommit(boolean arg0);

    public abstract Statement createStatement();

    public abstract void commit();

    public abstract void rollback();
}
