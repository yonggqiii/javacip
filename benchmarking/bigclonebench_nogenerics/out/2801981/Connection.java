// added by JavaCIP
public interface Connection {

    public abstract void commit();

    public abstract UNKNOWN_52 createStatement();

    public abstract void rollback();

    public abstract void setAutoCommit(boolean arg0);

    public abstract PreparedStatement prepareStatement(String arg0);
}
