// added by JavaCIP
public interface PreparedStatement {

    public abstract ResultSet executeQuery();

    public abstract void close();

    public abstract void setInt(int arg0, int arg1);

    public abstract void executeUpdate();
}
