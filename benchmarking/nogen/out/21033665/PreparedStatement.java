// added by JavaCIP
public interface PreparedStatement {

    public abstract void clearWarnings();

    public abstract void setObject(int arg0, Object arg1);

    public abstract void clearParameters();

    public abstract int executeUpdate();

    public abstract void setNull(int arg0, int arg1);

    public abstract void close();
}
