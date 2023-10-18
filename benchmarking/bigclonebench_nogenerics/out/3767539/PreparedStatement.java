// added by JavaCIP
public interface PreparedStatement {

    public abstract void setTimestamp(int arg0, Timestamp arg1);

    public abstract void setString(int arg0, String arg1);

    public abstract void executeUpdate();

    public abstract void setBlob(int arg0, Object arg1);

    public abstract void setNull(int arg0, boolean arg1);

    public abstract void close();
}
