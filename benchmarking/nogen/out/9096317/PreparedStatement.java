// added by JavaCIP
public interface PreparedStatement {

    public abstract void setLong(int arg0, long arg1);

    public abstract void setTimestamp(int arg0, boolean arg1);

    public abstract int executeUpdate();

    public abstract void close();
}
