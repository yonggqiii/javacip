// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_ACCEPTED = 0;

    public static int HTTP_OK = 0;

    public abstract int getResponseCode();

    public abstract InputStream getInputStream();

    public abstract void disconnect();
}
