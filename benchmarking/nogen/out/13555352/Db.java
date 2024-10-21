// added by JavaCIP
public interface Db {

    public abstract void begin();

    public abstract PreparedStatement prepareStatement(String arg0);

    public abstract void commit();

    public abstract void rollback();
}
