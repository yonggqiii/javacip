// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_OK = false;

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setConnectTimeout(boolean arg0);

    public abstract boolean getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract UNKNOWN_96 getInputStream();

    public abstract void setUseCaches(boolean arg0);

    public abstract UNKNOWN_134 getOutputStream();
}
