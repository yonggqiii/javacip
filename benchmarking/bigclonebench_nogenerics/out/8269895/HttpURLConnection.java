// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_UNAUTHORIZED = false;

    public static boolean HTTP_OK = false;

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract boolean getResponseCode();

    public abstract boolean getInputStream();

    public abstract boolean getResponseMessage();
}
