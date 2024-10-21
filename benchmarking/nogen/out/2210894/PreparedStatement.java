// added by JavaCIP
public interface PreparedStatement {

    public abstract int executeUpdate();

    public abstract ResultSet getGeneratedKeys();

    public abstract void close();
}
