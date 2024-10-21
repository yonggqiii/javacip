// added by JavaCIP
public interface Statement {

    public static boolean RETURN_GENERATED_KEYS = false;

    public static boolean NO_GENERATED_KEYS = false;

    public abstract int executeUpdate(String arg0, boolean arg1);

    public abstract ResultSet getGeneratedKeys();
}
