// added by JavaCIP
public interface PreparedStatement {

    public abstract void setString(int arg0, boolean arg1);

    public abstract void setTimestamp(int arg0, boolean arg1);

    public abstract void executeUpdate();

    public abstract void close();
}
