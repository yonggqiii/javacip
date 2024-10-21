// added by JavaCIP
public interface Connection {

    public abstract Statement createStatement();

    public abstract void rollback();

    public abstract void close();
}
