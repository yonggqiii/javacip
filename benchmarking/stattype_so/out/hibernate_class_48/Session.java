// added by JavaCIP
public interface Session {

    public abstract Transaction beginTransaction();

    public abstract void save(String arg0);

    public abstract void close();
}
