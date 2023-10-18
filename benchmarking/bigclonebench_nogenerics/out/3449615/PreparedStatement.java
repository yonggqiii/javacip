// added by JavaCIP
public interface PreparedStatement {

    public abstract void setString(int arg0, String arg1);

    public abstract void executeUpdate();

    public abstract void setInt(int arg0, int arg1);

    public abstract void setBinaryStream(int arg0, InputStream arg1, int arg2);

    public abstract void close();
}
