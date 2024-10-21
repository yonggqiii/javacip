// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_OK = false;

    public abstract void disconnect();

    public abstract void connect();

    public abstract boolean getResponseCode();

    public abstract boolean getInputStream();

    public abstract void setUseCaches(boolean arg0);
}
