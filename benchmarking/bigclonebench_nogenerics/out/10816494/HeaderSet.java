// added by JavaCIP
public interface HeaderSet {

    public static boolean NAME = false;

    public static boolean TYPE = false;

    public static boolean LENGTH = false;

    public abstract boolean getResponseCode();

    public abstract void setHeader(boolean arg0, String arg1);

    public abstract void setHeader(boolean arg0, Long arg1);
}
