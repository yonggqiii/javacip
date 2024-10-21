// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public abstract void connect();

    public abstract void setConnectTimeout(int arg0);

    public abstract boolean getResponseCode();
}
