// added by JavaCIP
public interface Connection {

    public abstract void commit();

    public abstract Statement createStatement();

    public abstract Statement createStatement(boolean arg0, boolean arg1);

    public abstract void rollback();

    public abstract UNKNOWN_69 getMetaData();

    public abstract PreparedStatement prepareStatement(String arg0);
}
