// added by JavaCIP
public interface PreparedStatement {

    public abstract void setInt(int arg0, boolean arg1);

    public abstract void setInt(int arg0, int arg1);

    public abstract void setString(int arg0, String arg1);

    public abstract int executeUpdate();
}
