// added by JavaCIP
public interface DatabaseAdapter {

    public static DatabaseAdapter getInstance() {
        return null;
    }

    public abstract void commit();

    public abstract void rollback();

    public abstract Long getSequenceNextValue(CustomSequenceType arg0);

    public abstract PreparedStatement prepareStatement(String arg0);
}
