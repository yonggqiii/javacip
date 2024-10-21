// added by JavaCIP
public interface UserTransaction {

    public abstract void begin();

    public abstract void commit();

    public abstract void rollback();
}
