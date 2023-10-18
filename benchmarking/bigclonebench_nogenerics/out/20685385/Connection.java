// added by JavaCIP
public interface Connection {

    public abstract void commit();

    public abstract void setReadOnly(boolean arg0);

    public abstract UNKNOWN_109 createStatement();

    public abstract void rollback();

    public abstract void setAutoCommit(boolean arg0);
}
