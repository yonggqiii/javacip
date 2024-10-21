// added by JavaCIP
public interface PreparedStatement {

    public abstract void setTimestamp(int arg0, Timestamp arg1);

    public abstract void setString(int arg0, String arg1);

    public abstract int executeUpdate();

    public abstract void setLong(int arg0, long arg1);

    public abstract void close();
}
