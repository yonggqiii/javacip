// added by JavaCIP
public interface SyrupConnection {

    public abstract PreparedStatement prepareStatementFromCache(boolean arg0);

    public abstract void commit();

    public abstract void rollback();
}
