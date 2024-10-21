// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public static int HTTP_BAD_REQUEST = 0;

    public abstract int getResponseCode();

    public abstract String getResponseMessage();

    public abstract String getContentType();
}
