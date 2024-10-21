// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_NOT_FOUND = 0;

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract void disconnect();
}
