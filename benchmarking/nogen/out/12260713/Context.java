// added by JavaCIP
public interface Context {

    public abstract Connection getConnection();

    public abstract void put(boolean arg0, String arg1);

    public abstract void releaseConnection(Connection arg0);
}
