// added by JavaCIP
public interface PreparedStatement {

    public abstract void setString(int arg0, boolean arg1);

    public abstract void setInt(int arg0, boolean arg1);

    public abstract void setDouble(int arg0, boolean arg1);

    public abstract int executeUpdate();
}
