// added by JavaCIP
public interface ERXJDBCConnectionBroker {

    public static ERXJDBCConnectionBroker connectionBrokerForEntityNamed(String arg0) {
        return null;
    }

    public abstract Connection getConnection();

    public abstract void freeConnection(Connection arg0);
}
