// added by JavaCIP
public interface Connection {

    public abstract void setAutoCommit(boolean arg0);

    public abstract PreparedStatement prepareStatement(boolean arg0);

    public abstract void rollback();

    public abstract void commit();
}
