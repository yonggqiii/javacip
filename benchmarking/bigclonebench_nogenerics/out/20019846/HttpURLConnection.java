// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract int getResponseCode();

    public abstract boolean getURL();

    public abstract InputStream getInputStream();
}
