// added by JavaCIP
public interface UNKNOWN_149 {

    public abstract void commit();

    public abstract void rollback();

    public abstract int getTransactionIsolation();

    public abstract PreparedStatement prepareStatement(String arg0);

    public abstract void setTransactionIsolation(boolean arg0);

    public abstract void setTransactionIsolation(int arg0);

    public abstract void setAutoCommit(boolean arg0);
}
