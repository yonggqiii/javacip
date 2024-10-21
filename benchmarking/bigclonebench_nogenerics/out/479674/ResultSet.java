// added by JavaCIP
public interface ResultSet {

    public static boolean TYPE_SCROLL_INSENSITIVE = false;

    public static boolean CONCUR_UPDATABLE = false;

    public abstract boolean next();

    public abstract long getLong(int arg0);

    public abstract void close();
}
