// added by JavaCIP
public interface PreparedStatement {

    public abstract void setLong(int arg0, long arg1);

    public abstract void setString(int arg0, Object arg1);

    public abstract int executeUpdate();

    public abstract ResultSet executeQuery();
}
