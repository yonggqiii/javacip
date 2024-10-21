// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_OK = false;

    public abstract void disconnect();

    public abstract void connect();

    public abstract boolean getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void addRequestProperty(String arg0, String arg1);

    public abstract void addRequestProperty(String arg0, boolean arg1);

    public abstract void setAllowUserInteraction(boolean arg0);
}
