// added by JavaCIP
public interface Connection extends HashMap<String, String> {

    public abstract void setAutoCommit(boolean arg0);

    public abstract PreparedStatement prepareStatement(String arg0);

    public abstract void commit();

    public abstract void rollback();
}
