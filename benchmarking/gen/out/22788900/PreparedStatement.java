// added by JavaCIP
public interface PreparedStatement {

    public abstract void setLong(int arg0, long arg1);

    public abstract ResultSet executeQuery();

    public abstract void setString(int arg0, java.io.Serializable arg1);

    public abstract byte executeUpdate();

    public abstract void setBoolean(int arg0, boolean arg1);
}
