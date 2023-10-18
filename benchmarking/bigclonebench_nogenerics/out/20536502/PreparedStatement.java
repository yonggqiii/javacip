// added by JavaCIP
public interface PreparedStatement {

    public abstract void setInt(int arg0, int arg1);

    public abstract int executeUpdate();

    public abstract void close();

    public abstract ResultSet executeQuery();
}
