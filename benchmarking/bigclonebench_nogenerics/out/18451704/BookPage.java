// added by JavaCIP
public interface BookPage {

    public static boolean TYPE_RESOURCE = false;

    public static boolean TYPE_URL = false;

    public static boolean TYPE_IMAGE = false;

    public abstract boolean getType();

    public abstract UNKNOWN_48 getUri();
}
