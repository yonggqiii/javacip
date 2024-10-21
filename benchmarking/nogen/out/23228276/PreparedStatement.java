// added by JavaCIP
public interface PreparedStatement {

    public abstract void setTime(int arg0, boolean arg1);

    public abstract void setInt(int arg0, Integer arg1);

    public abstract void executeUpdate();

    public abstract void close();
}
