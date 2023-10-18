// added by JavaCIP
public interface Connection {

    public abstract PreparedStatement prepareStatement(String arg0);

    public abstract boolean getAutoCommit();

    public abstract void commit();

    public abstract void rollback();
}
