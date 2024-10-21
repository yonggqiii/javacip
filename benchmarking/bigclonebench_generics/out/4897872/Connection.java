// added by JavaCIP
public interface Connection {

    public static boolean TRANSACTION_READ_COMMITTED = false;

    public abstract void commit();

    public abstract void rollback();

    public abstract void close();

    public abstract PreparedStatement prepareStatement(boolean arg0);

    public abstract void setTransactionIsolation(boolean arg0);

    public abstract void setAutoCommit(boolean arg0);
}
