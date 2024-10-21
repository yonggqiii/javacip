// added by JavaCIP
public interface Session<A, B> {

    public abstract B beginTransaction();

    public abstract void delete(A arg0);

    public abstract void saveOrUpdate(A arg0);

    public abstract boolean isOpen();

    public abstract A get(java.io.Serializable arg0, B arg1);

    public abstract void close();

    public abstract B save(A arg0);
}
