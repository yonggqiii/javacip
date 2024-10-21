// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_OK = false;

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public abstract void connect();

    public abstract boolean getResponseCode();

    public abstract InputStream getInputStream();

    public abstract void setUseCaches(boolean arg0);
}
