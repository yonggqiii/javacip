// added by JavaCIP
public interface Statement {

    public abstract void executeUpdate(String arg0, String[] arg1);

    public abstract ResultSet getGeneratedKeys();

    public abstract void close();
}
