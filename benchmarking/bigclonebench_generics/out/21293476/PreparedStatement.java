// added by JavaCIP
public interface PreparedStatement {

    public abstract void setTime(int arg0, boolean arg1);

    public abstract void executeUpdate();

    public abstract void setInt(int arg0, int arg1);

    public abstract void setInt(int arg0, boolean arg1);

    public abstract void setDate(int arg0, boolean arg1);

    public abstract void close();

    public abstract ResultSet executeQuery();

    public abstract void setString(int arg0, boolean arg1);
}
