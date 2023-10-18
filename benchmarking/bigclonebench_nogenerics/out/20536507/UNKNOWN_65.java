// added by JavaCIP
public interface UNKNOWN_65 {

    public abstract void commit();

    public abstract Statement createStatement();

    public abstract void rollback();

    public abstract Savepoint setSavepoint();

    public abstract void setAutoCommit(boolean arg0);

    public abstract PreparedStatement prepareStatement(String arg0);
}
