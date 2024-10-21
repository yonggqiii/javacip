// added by JavaCIP
public interface Connection {

    public abstract PreparedStatement prepareStatement(String arg0);

    public abstract boolean getAutoCommit();

    public abstract void rollback();

    public abstract void setAutoCommit(boolean arg0);
}
