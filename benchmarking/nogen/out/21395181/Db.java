// added by JavaCIP
public interface Db {

    public abstract void begin();

    public abstract void commit();

    public abstract void executeUpdate(PreparedStatement arg0);

    public abstract void rollback();

    public abstract void safeClose();

    public abstract PreparedStatement prepareStatement(String arg0);
}
