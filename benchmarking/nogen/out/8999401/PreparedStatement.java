// added by JavaCIP
public interface PreparedStatement {

    public abstract void executeUpdate();

    public abstract void setInt(int arg0, boolean arg1);

    public abstract void close();

    public abstract void setString(int arg0, boolean arg1);

    public abstract void clearParameters();
}
