// added by JavaCIP
public interface Session {

    public abstract Transaction getTransaction();

    public abstract void saveOrUpdate(User arg0);

    public abstract void close();
}
