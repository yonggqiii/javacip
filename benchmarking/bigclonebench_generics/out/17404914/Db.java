// added by JavaCIP
public interface Db {

    public abstract void begin();

    public abstract ResultSet executeQuery(PreparedStatement arg0);

    public abstract int executeUpdate(PreparedStatement arg0);

    public abstract void commitUnless();

    public abstract void rollback();

    public abstract PreparedStatement prepareStatement(String arg0);
}
