// added by JavaCIP
public interface Connection {

    public abstract void commit();

    public abstract Statement createStatement();

    public abstract void rollback(Savepoint arg0);

    public abstract void rollback();

    public abstract Savepoint setSavepoint();

    public abstract void setAutoCommit(boolean arg0);
}
