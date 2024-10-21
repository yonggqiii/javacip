// added by JavaCIP
public interface PreparedStatement {

    public abstract void setObject(int arg0, boolean arg1);

    public abstract void setObject(int arg0, Object arg1);

    public abstract ResultSet executeQuery();

    public abstract void close();

    public abstract byte executeUpdate();
}
