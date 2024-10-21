// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract void setRequestMethod(String arg0);

    public abstract int getContentLength();

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract InputStream getInputStream();
}
