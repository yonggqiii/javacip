// added by JavaCIP
public interface Connection {

    public abstract void rollback();

    public abstract void close();

    public abstract PreparedStatement prepareStatement(String arg0);

    public abstract CallableStatement prepareCall(String arg0);

    public abstract void commit();
}
