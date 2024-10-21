// added by JavaCIP
public interface PreparedStatement {

    public abstract void setString(int arg0, String arg1);

    public abstract int executeUpdate();

    public abstract ResultSet getGeneratedKeys();
}
