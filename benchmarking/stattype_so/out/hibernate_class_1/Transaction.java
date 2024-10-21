// added by JavaCIP
public interface Transaction {

    public abstract void begin();

    public abstract void commit();

    public abstract void rollback();
}
