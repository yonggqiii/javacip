// added by JavaCIP
public interface ResultSet {

    public static boolean TYPE_FORWARD_ONLY = false;

    public static boolean CONCUR_READ_ONLY = false;

    public abstract UNKNOWN_95 getMetaData();

    public abstract boolean next();

    public abstract boolean getObject(int arg0);
}
