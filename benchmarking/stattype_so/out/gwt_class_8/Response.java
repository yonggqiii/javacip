// added by JavaCIP
public interface Response {

    public static boolean SC_UNAUTHORIZED = false;

    public static boolean SC_OK = false;

    public abstract boolean getStatusCode();

    public abstract boolean getStatusText();

    public abstract boolean getText();
}
