// added by JavaCIP
public interface ResultSet {

    public static boolean TYPE_FORWARD_ONLY = false;

    public static boolean CONCUR_UPDATABLE = false;

    public static boolean TYPE_SCROLL_SENSITIVE = false;

    public abstract boolean next();

    public abstract int getInt(int arg0);
}
