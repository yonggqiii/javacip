// added by JavaCIP
public interface UNKNOWN_2626 {

    public abstract UNKNOWN_2367 createStatement();

    public abstract void releaseSavepoint(Savepoint arg0);

    public abstract void rollback(Savepoint arg0);

    public abstract void rollback();

    public abstract Savepoint setSavepoint(String arg0);

    public abstract void setAutoCommit(boolean arg0);

    public abstract PreparedStatement prepareStatement(String arg0);
}
