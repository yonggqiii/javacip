// added by JavaCIP
public interface DatabaseAdapter {

    public static DatabaseAdapter getInstance() {
        return null;
    }

    public abstract PreparedStatement prepareStatement(String arg0);

    public abstract void commit();

    public abstract void rollback();
}
