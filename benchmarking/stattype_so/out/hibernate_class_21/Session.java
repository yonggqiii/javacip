// added by JavaCIP
public interface Session {

    public abstract Transaction getTransaction();

    public abstract void close();
}
