// added by JavaCIP
public interface Statement {

    public abstract void execute(String arg0);

    public abstract void close();

    public abstract ResultSet executeQuery(String arg0);
}
