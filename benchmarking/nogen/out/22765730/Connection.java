// added by JavaCIP
public interface Connection {

    public abstract PreparedStatement prepareStatement(boolean arg0);

    public abstract void commit();

    public abstract void rollback();

    public abstract void close();
}
