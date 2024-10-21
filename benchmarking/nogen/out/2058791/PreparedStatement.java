// added by JavaCIP
public interface PreparedStatement {

    public abstract void setInt(int arg0, int arg1);

    public abstract boolean executeUpdate();

    public abstract void close();

    public abstract void setString(int arg0, String arg1);
}
