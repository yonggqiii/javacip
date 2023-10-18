// added by JavaCIP
public interface Connection {

    public abstract void commit();

    public abstract Statement createStatement();

    public abstract void rollback();

    public abstract void close();

    public abstract PreparedStatement prepareStatement(String arg0);
}
