// added by JavaCIP
public class Connection {

    public static boolean TRANSACTION_READ_COMMITTED;

    public void commit() {
    }

    public boolean isReadOnly() {
        return false;
    }

    public boolean getAutoCommit() {
        return false;
    }

    public void rollback() {
    }

    public boolean getTransactionIsolation() {
        return false;
    }

    public void close() {
    }

    public PreparedStatement prepareStatement(String arg0, boolean arg1, boolean arg2) {
        return null;
    }

    public PreparedStatement prepareStatement(String arg0) {
        return null;
    }

    Connection() {
        super();
    }
}
