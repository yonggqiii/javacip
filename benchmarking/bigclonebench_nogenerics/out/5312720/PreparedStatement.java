// added by JavaCIP
public interface PreparedStatement {

    public abstract void setString(int arg0, boolean arg1);

    public abstract int executeUpdate();

    public abstract void setLong(int arg0, Integer arg1);

    public abstract void setLong(int arg0, long arg1);

    public abstract void setLong(int arg0, boolean arg1);

    public abstract void setFloat(int arg0, boolean arg1);

    public abstract void setNull(int arg0, boolean arg1);

    public abstract void close();
}
