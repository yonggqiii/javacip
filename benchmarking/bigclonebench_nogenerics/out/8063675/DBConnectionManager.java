// added by JavaCIP
public interface DBConnectionManager {

    public static DBConnectionManager getInstance() {
        return null;
    }

    public abstract Connection getConnection(String arg0);

    public abstract void freeConnection(String arg0, Connection arg1);
}
