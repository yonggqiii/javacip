// added by JavaCIP
public interface PreparedStatement {

    public abstract int executeUpdate();

    public abstract void setInt(int arg0, double arg1);

    public abstract ResultSet executeQuery();

    public abstract void setString(int arg0, String arg1);

    public abstract void clearParameters();
}
