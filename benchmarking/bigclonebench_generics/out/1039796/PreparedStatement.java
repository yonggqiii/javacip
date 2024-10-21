// added by JavaCIP
public interface PreparedStatement {

    public static boolean RETURN_GENERATED_KEYS = false;

    public abstract void setObject(int arg0, MLPApprox arg1);

    public abstract void setString(int arg0, String arg1);

    public abstract ResultSet getGeneratedKeys();

    public abstract void executeUpdate();

    public abstract void setInt(int arg0, int arg1);

    public abstract void setDate(int arg0, Date arg1);
}
