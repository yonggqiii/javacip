// added by JavaCIP
public interface PreparedStatement {

    public abstract void executeUpdate();

    public abstract void close();

    public abstract ResultSet executeQuery();
}
