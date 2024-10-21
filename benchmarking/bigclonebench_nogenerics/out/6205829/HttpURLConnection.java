// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract int getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract void setDoInput(boolean arg0);
}
