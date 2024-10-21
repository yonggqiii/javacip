// added by JavaCIP
public interface Statement {

    public static int SUCCESS_NO_INFO = 0;

    public static int EXECUTE_FAILED = 0;

    public abstract void executeUpdate(String arg0);

    public abstract void addBatch(boolean arg0);

    public abstract int[] executeBatch();

    public abstract void close();
}
