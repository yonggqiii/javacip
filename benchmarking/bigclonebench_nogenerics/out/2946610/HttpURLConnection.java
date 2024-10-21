// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setConnectTimeout(int arg0);

    public abstract int getResponseCode();

    public abstract boolean getInputStream();
}
