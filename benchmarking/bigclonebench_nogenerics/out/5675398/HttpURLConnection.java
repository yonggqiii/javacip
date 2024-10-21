// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract void disconnect();

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, boolean arg1);

    public abstract int getResponseCode();

    public abstract boolean getInputStream();

    public abstract boolean getResponseMessage();
}
