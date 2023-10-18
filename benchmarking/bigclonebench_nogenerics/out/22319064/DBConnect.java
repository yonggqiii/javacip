// added by JavaCIP
public interface DBConnect {

    public static DBConnect createDBConnect() {
        return null;
    }

    public abstract Connection getConnection();

    public abstract void close();
}
