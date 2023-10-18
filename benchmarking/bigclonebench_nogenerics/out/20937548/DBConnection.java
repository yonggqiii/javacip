// added by JavaCIP
public interface DBConnection {

    public abstract void commit();

    public abstract void rollback();

    public abstract void setAutoCommit(boolean arg0);

    public abstract void release();

    public abstract PreparedStatement prepareStatement(String arg0);
}
