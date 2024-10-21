// added by JavaCIP
public interface Statement {

    public abstract void execute(String arg0);

    public abstract boolean executeUpdate(String arg0);

    public abstract ResultSet executeQuery(String arg0);

    public abstract void close();
}
