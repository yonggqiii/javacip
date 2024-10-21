// added by JavaCIP
public interface PreparedStatement {

    public abstract boolean getResultSetType();

    public abstract void executeUpdate();

    public abstract boolean getResultSetConcurrency();

    public abstract void setInt(int arg0, int arg1);

    public abstract void setQueryTimeout(boolean arg0);

    public abstract void close();

    public abstract ResultSet executeQuery();

    public abstract void setString(int arg0, String arg1);
}
