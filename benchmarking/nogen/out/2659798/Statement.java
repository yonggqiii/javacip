// added by JavaCIP
public interface Statement {

    public abstract void execute(boolean arg0);

    public abstract void setFetchSize(int arg0);

    public abstract ResultSet executeQuery(String arg0);
}
