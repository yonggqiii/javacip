// added by JavaCIP
public interface Session {

    public abstract Transaction beginTransaction();

    public abstract void close();
}
