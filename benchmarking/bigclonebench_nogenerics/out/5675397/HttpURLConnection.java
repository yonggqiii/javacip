// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public static int HTTP_NO_CONTENT = 0;

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract boolean getResponseMessage();
}
