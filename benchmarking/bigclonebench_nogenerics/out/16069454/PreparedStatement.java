// added by JavaCIP
public interface PreparedStatement {

    public abstract void setInt(int arg0, int arg1);

    public abstract void setInt(int arg0, boolean arg1);

    public abstract void setString(int arg0, Object arg1);

    public abstract void executeUpdate();
}
