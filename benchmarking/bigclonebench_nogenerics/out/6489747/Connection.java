// added by JavaCIP
public interface Connection {

    public static boolean TRANSACTION_READ_COMMITTED = false;

    public abstract void commit();

    public abstract boolean isReadOnly();

    public abstract boolean getAutoCommit();

    public abstract void rollback();

    public abstract boolean getTransactionIsolation();

    public abstract void close();

    public abstract PreparedStatement prepareStatement(String arg0, boolean arg1, boolean arg2);

    public abstract PreparedStatement prepareStatement(String arg0);
}
